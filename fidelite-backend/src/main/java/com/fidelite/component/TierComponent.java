package com.fidelite.component;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Tier;
import com.fidelite.repository.TierRepository;

import lombok.RequiredArgsConstructor;

// Data-access component for Tier entities.
@Component
@RequiredArgsConstructor
public class TierComponent {

    private final TierRepository tierRepository;

    public Tier save(Tier tier) {
        return tierRepository.save(tier);
    }

    // Finds a tier by ID, throwing a typed exception if absent.
    public Tier findById(Long id) throws NotFoundElementException {
        return tierRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(
                        String.format("tier with id [%s] not found", id)));
    }

    // Returns all tiers for the given merchant.
    public List<Tier> findByMerchantId(UUID merchantId) {
        return tierRepository.findByMerchantIdMerchant(merchantId);
    }

    // Ordered highest-threshold-first: used to find the customer's tier by their point balance.
    public List<Tier> findByMerchantIdOrderByMinPointsDesc(UUID merchantId) {
        return tierRepository.findByMerchantIdMerchantOrderByMinPointsDesc(merchantId);
    }

    public void deleteById(Long id) {
        tierRepository.deleteById(id);
    }
}
