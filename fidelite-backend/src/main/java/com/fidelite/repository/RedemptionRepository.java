package com.fidelite.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fidelite.models.Redemption;

@Repository
public interface RedemptionRepository extends JpaRepository<Redemption, Long> {

    // Get paginated redemption history for a specific loyalty card
    Page<Redemption> findByCardLoyaltyCardId(UUID cardId, Pageable pageable);

    // Get all redemptions made for a specific reward (used for reward usage stats)
    List<Redemption> findByRewardRewardID(UUID rewardId);
}
