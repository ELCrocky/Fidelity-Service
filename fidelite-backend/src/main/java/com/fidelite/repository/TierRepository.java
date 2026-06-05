package com.fidelite.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fidelite.models.Tier;

@Repository
public interface TierRepository extends JpaRepository<Tier, Long> {

    // Get all tiers defined by a specific merchant
    List<Tier> findByMerchantIdMerchant(UUID merchantId);

    // Get tiers sorted highest-first so the first match is the best eligible tier
    List<Tier> findByMerchantIdMerchantOrderByMinPointsDesc(UUID merchantId);
}
