package com.fidelite.service;

import com.fidelite.component.ClearingCompanyComponent;
import com.fidelite.component.MerchantComponent;
import com.fidelite.component.SettlementComponent;
import com.fidelite.component.SettlementLineComponent;
import com.fidelite.component.SettlementPoolComponent;
import com.fidelite.component.TransactionComponent;
import com.fidelite.dto.requests.SettlementRequestDTO;
import com.fidelite.dto.responseDTO.SettlementResponseDTO;
import com.fidelite.enums.TransactionType;
import com.fidelite.exceptions.rest.BadRequestRestException;
import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.mappers.SettlementMapper;
import com.fidelite.models.ClearingCompany;
import com.fidelite.models.Merchant;
import com.fidelite.models.Settlement;
import com.fidelite.models.SettlementLine;
import com.fidelite.models.SettlementPool;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Service layer for period-end settlement calculation and history queries.
@Service
@RequiredArgsConstructor
public class SettlementService {
    private final SettlementComponent settlementComponent;
    private final SettlementLineComponent settlementLineComponent;
    private final SettlementPoolComponent settlementPoolComponent;
    private final MerchantComponent merchantComponent;
    private final TransactionComponent transactionComponent;
    private final ClearingCompanyComponent clearingCompanyComponent;
    private final SettlementMapper settlementMapper;

    /**
     * Runs a period-end settlement for a pool.
     * Direction: netPoints = issued - redeemed.
     *   POSITIVE (over-issued)  -> merchant OWES the pool.
     *   NEGATIVE (over-redeemed) -> merchant is OWED by the pool.
     * A separate commission is calculated per merchant; it is deducted from the pool balance
     * and added to the clearing company's revenue.
     */
    @Transactional
    public SettlementResponseDTO runSettlement(SettlementRequestDTO request) {
        try {
            SettlementPool pool = settlementPoolComponent.findById(request.getPoolId());

            // Duplicate-settlement guard: has this period already been calculated?
            if (settlementComponent.findByPoolIdAndPeriodStart(
                    pool.getSpId(), request.getPeriodStart()).isPresent()) {
                throw new BadRequestRestException(String.format(
                        "settlement for pool [%s] period starting [%s] already exists",
                        pool.getSpId(), request.getPeriodStart()));
            }

            // Convert period boundaries to timestamps (start of day / end of day).
            LocalDateTime start = request.getPeriodStart().atStartOfDay();
            LocalDateTime end = request.getPeriodEnd().atTime(LocalTime.MAX);

            // Build the settlement header (lines added below).
            Settlement settlement = Settlement.builder()
                    .pool(pool)
                    .periodStart(request.getPeriodStart())
                    .periodEnd(request.getPeriodEnd())
                    .build();

            List<Merchant> merchants = merchantComponent.findByPoolId(pool.getSpId());

            long totalIssued = 0;
            long totalRedeemed = 0;
            BigDecimal totalCommission = BigDecimal.ZERO;
            List<SettlementLine> lines = new ArrayList<>();

            for (Merchant merchant : merchants) {
                long issued = transactionComponent.sumPointsByMerchantAndTypeAndPeriod(
                        merchant.getIdMerchant(), TransactionType.EARN, start, end);
                long redeemed = transactionComponent.sumPointsByMerchantAndTypeAndPeriod(
                        merchant.getIdMerchant(), TransactionType.REDEEM, start, end);

                long netPoints = issued - redeemed; // positive: owes pool; negative: owed by pool
                BigDecimal netAmount = BigDecimal.valueOf(netPoints).multiply(pool.getPointValue());

                SettlementLine line = SettlementLine.builder()
                        .settlement(settlement)
                        .merchant(merchant)
                        .pointsIssued(issued)
                        .pointsRedeemed(redeemed)
                        .netPoints(netPoints)
                        .netAmount(netAmount)
                        .build();
                lines.add(line);

                totalIssued += issued;
                totalRedeemed += redeemed;
                // One merchant = one invoice = one commission charge.
                totalCommission = totalCommission.add(pool.getCommissionPerInvoice());
            }

            // Pool-wide net totals.
            long netPointsTotal = totalIssued - totalRedeemed;
            BigDecimal netAmountTotal = BigDecimal.valueOf(netPointsTotal).multiply(pool.getPointValue());

            settlement.setPointsIssued(totalIssued);
            settlement.setPointsRedeemed(totalRedeemed);
            settlement.setNetAmount(netAmountTotal);
            settlement.setCommision(totalCommission);
            settlement.setLines(lines);

            // Deduct commission from pool balance.
            pool.setBalance(pool.getBalance().subtract(totalCommission));
            settlementPoolComponent.save(pool);

            // Add commission to clearing company revenue.
            ClearingCompany company = pool.getClearingCompany();
            company.setTotalRevenue(company.getTotalRevenue().add(totalCommission));
            clearingCompanyComponent.save(company);

            // Persist the settlement with its lines (cascade saves the lines).
            Settlement saved = settlementComponent.save(settlement);
            return settlementMapper.toResponse(saved);

        } catch (NotFoundElementException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    // Returns the paginated settlement history for the given pool.
    public Page<SettlementResponseDTO> getByPool(UUID poolId, Pageable pageable) {
        return settlementComponent.findByPoolId(poolId, pageable)
                .map(settlementMapper::toResponse);
    }
}
