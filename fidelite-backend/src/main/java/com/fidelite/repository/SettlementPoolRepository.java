package com.fidelite.repository;

import com.fidelite.models.SettlementPool;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementPoolRepository extends JpaRepository<SettlementPool, UUID> {

    // Get all settlement pools managed by a specific clearing company
    List<SettlementPool> findByClearingCompanyCcId(UUID clearingCompanyId);
}
