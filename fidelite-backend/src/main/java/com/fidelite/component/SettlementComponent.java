package com.fidelite.component;

import com.fidelite.models.Settlement;
import com.fidelite.repository.SettlementRepository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

// Data-access component for Settlement entities.
@Component
@RequiredArgsConstructor
public class SettlementComponent {

    private final SettlementRepository settlementRepository;

    public Settlement save(Settlement settlement) {
        return settlementRepository.save(settlement);
    }

    // Returns the paginated settlement history for the given pool.
    public Page<Settlement> findByPoolId(UUID poolId, Pageable pageable) {
        return settlementRepository.findByPoolSpId(poolId, pageable);
    }

    // Duplicate-settlement guard: checks whether this period has already been calculated.
    public Optional<Settlement> findByPoolIdAndPeriodStart(UUID poolId, LocalDate periodStart) {
        return settlementRepository.findByPoolSpIdAndPeriodStart(poolId, periodStart);
    }
}
