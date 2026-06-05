package com.fidelite.repository;

import com.fidelite.models.SettlementLine;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementLineRepository extends JpaRepository<SettlementLine, Long> {

    // Get all per-merchant line items for a specific settlement report
    List<SettlementLine> findBySettlementSettlementId(Long settlementId);

    // Get all settlement lines for a specific merchant across all settlements
    List<SettlementLine> findByMerchantIdMerchant(java.util.UUID merchantId);
}
