package com.fidelite.service;

import com.fidelite.component.LoyaltyCardComponent;
import com.fidelite.component.RedemptionComponent;
import com.fidelite.component.RewardComponent;
import com.fidelite.dto.requests.RedemptionRequestDTO;
import com.fidelite.dto.requests.TransactionRequestDTO;
import com.fidelite.dto.responseDTO.RedemptionResponseDTO;
import com.fidelite.enums.TransactionType;
import com.fidelite.exceptions.rest.BadRequestRestException;
import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.mappers.RedemptionMapper;
import com.fidelite.models.LoyaltyCard;
import com.fidelite.models.Redemption;
import com.fidelite.models.Reward;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Service layer for reward redemption; handles balance checks and idempotency via TransactionService.
@Service
@RequiredArgsConstructor
public class RedemptionService {
    private final RedemptionComponent redemptionComponent;
    private final LoyaltyCardComponent loyaltyCardComponent;
    private final RewardComponent rewardComponent;
    private final TransactionService transactionService;
    private final RedemptionMapper redemptionMapper;

    /**
     * Reward redemption. Steps:
     * 1. Find the card and the reward.
     * 2. Check whether the balance is sufficient.
     * 3. Delegate a REDEEM transaction to TransactionService (balance deduction + idempotency handled there).
     * 4. Persist the Redemption record.
     * Everything is atomic (@Transactional).
     */
    @Transactional
    public RedemptionResponseDTO redeem(RedemptionRequestDTO request) {
        try {
            LoyaltyCard card = loyaltyCardComponent.findById(request.getCardId());
            Reward reward = rewardComponent.findById(request.getRewardId());

            // Balance check (early, for a clear error message).
            if (card.getPointsBalance() < reward.getCostPoints()) {
                throw new BadRequestRestException(String.format(
                        "insufficient balance: card has [%d], reward costs [%d]",
                        card.getPointsBalance(), reward.getCostPoints()));
            }

            // REDEEM transaction: balance deduction and idempotency are handled here.
            TransactionRequestDTO txRequest = new TransactionRequestDTO();
            txRequest.setCardId(card.getLoyaltyCardId());
            txRequest.setPoints(reward.getCostPoints());
            txRequest.setType(TransactionType.REDEEM);
            txRequest.setIdempotencyKey(request.getIdempotencyKey());
            transactionService.create(txRequest);

            // Persist the redemption record.
            Redemption redemption = Redemption.builder()
                    .card(card)
                    .reward(reward)
                    .build();
            return redemptionMapper.toResponse(redemptionComponent.save(redemption));

        } catch (NotFoundElementException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    // Returns the paginated redemption history for the given loyalty card.
    public Page<RedemptionResponseDTO> getByCard(UUID cardId, Pageable pageable) {
        return redemptionComponent.findByCardId(cardId, pageable)
                .map(redemptionMapper::toResponse);
    }
}
