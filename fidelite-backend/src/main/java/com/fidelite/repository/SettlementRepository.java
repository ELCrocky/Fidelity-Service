package com.fidelite.repository;

import com.fidelite.models.Settlement;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    // Get all settlements that belong to a specific settlement pool
    List<Settlement> findByPoolSpId(UUID poolId);
}
