package com.fidelite.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fidelite.models.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {

    // Get all merchants that belong to a specific settlement pool
    List<Merchant> findByPoolSpId(UUID poolId);

    // Look up a merchant by their API key (used for request authentication)
    Optional<Merchant> findByApiKey(String apiKey);

    // Get merchants in a pool filtered by region (used for regional reporting)
    List<Merchant> findByPoolSpIdAndRegion(UUID poolId, String region);
}
