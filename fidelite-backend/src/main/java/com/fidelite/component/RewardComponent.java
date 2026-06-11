package com.fidelite.component;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Reward;
import com.fidelite.repository.RewardRepository;

import lombok.RequiredArgsConstructor;

// Data-access component for Reward entities.
@Component
@RequiredArgsConstructor
public class RewardComponent {

    private final RewardRepository rewardRepository;

    public Reward save(Reward reward) {
        return rewardRepository.save(reward);
    }

    // Finds a reward by UUID, throwing a typed exception if absent.
    public Reward findById(UUID id) throws NotFoundElementException {
        return rewardRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(
                        String.format("reward with id [%s] not found", id)));
    }

    // Returns all rewards offered by the given merchant.
    public List<Reward> findByMerchantId(UUID merchantId) {
        return rewardRepository.findByMerchantIdMerchant(merchantId);
    }

    // Returns rewards whose cost is within the given point balance.
    public List<Reward> findAffordable(UUID merchantId, int balance) {
        return rewardRepository.findByMerchantIdMerchantAndCostPointsLessThanEqual(merchantId, balance);
    }

    public void deleteById(UUID id) {
        rewardRepository.deleteById(id);
    }
}
