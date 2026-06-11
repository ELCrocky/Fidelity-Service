package com.fidelite.component;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.fidelite.models.Redemption;
import com.fidelite.repository.RedemptionRepository;

import lombok.RequiredArgsConstructor;

// Data-access component for Redemption entities.
@Component
@RequiredArgsConstructor
public class RedemptionComponent {

    private final RedemptionRepository redemptionRepository;

    public Redemption save(Redemption redemption) {
        return redemptionRepository.save(redemption);
    }

    // Returns the paginated redemption history for the given loyalty card.
    public Page<Redemption> findByCardId(UUID cardId, Pageable pageable) {
        return redemptionRepository.findByCardLoyaltyCardId(cardId, pageable);
    }

    // Returns all redemptions associated with the given reward.
    public List<Redemption> findByRewardId(UUID rewardId) {
        return redemptionRepository.findByRewardRewardID(rewardId);
    }
}
