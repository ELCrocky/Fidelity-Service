package com.fidelite.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fidelite.models.Reward;

@Repository
public interface RewardRepository extends JpaRepository<Reward, UUID> {

    // Get all rewards offered by a specific merchant
    List<Reward> findByMerchantIdMerchant(UUID merchantId);

    // Get rewards the customer can afford with their current points balance
    List<Reward> findByMerchantIdMerchantAndCostPointsLessThanEqual(UUID merchantId, int balance);
}
