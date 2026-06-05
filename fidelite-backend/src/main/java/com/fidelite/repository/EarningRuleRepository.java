package com.fidelite.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fidelite.models.EarningRule;

@Repository
public interface EarningRuleRepository extends JpaRepository<EarningRule, Long> {

    // Get all earning rules configured by a specific merchant
    List<EarningRule> findByMerchantIdMerchant(UUID merchantId);

    // Get rules that are currently active on a given date (respects validFrom/validTo bounds)
    @Query("""
            SELECT r FROM EarningRule r
            WHERE r.merchant.idMerchant = :merchantId
              AND (r.validFrom IS NULL OR r.validFrom <= :date)
              AND (r.validTo   IS NULL OR r.validTo   >= :date)
            """)
    List<EarningRule> findActiveRules(@Param("merchantId") UUID merchantId,
                                      @Param("date") LocalDate date);
}
