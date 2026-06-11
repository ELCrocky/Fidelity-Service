package com.fidelite.repository;

import com.fidelite.models.SettlementLine;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementLineRepository extends JpaRepository<SettlementLine, Long> {

    // Get all per-merchant line items for a specific settlement report
    List<SettlementLine> findBySettlementSettlementId(Long settlementId);

    // Get all settlement lines for a specific merchant (list form, used in service)
    List<SettlementLine> findByMerchantIdMerchant(UUID merchantId);

    // Get paginated settlement history for a specific merchant (used in merchant panel)
    Page<SettlementLine> findByMerchantIdMerchant(UUID merchantId, Pageable pageable);
}
