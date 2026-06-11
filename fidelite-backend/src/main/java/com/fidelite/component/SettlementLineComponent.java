package com.fidelite.component;

import com.fidelite.models.SettlementLine;
import com.fidelite.repository.SettlementLineRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

// Data-access component for SettlementLine entities.
@Component
@RequiredArgsConstructor
public class SettlementLineComponent {

    private final SettlementLineRepository settlementLineRepository;

    public SettlementLine save(SettlementLine line) {
        return settlementLineRepository.save(line);
    }

    // Returns all settlement lines belonging to the given settlement.
    public List<SettlementLine> findBySettlementId(Long settlementId) {
        return settlementLineRepository.findBySettlementSettlementId(settlementId);
    }

    // A merchant's period history (pool situation panel).
    public Page<SettlementLine> findByMerchantId(UUID merchantId, Pageable pageable) {
        return settlementLineRepository.findByMerchantIdMerchant(merchantId, pageable);
    }
}
