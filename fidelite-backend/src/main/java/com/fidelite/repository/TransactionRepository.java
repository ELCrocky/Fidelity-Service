package com.fidelite.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fidelite.dto.responseDTO.ProductSalesDTO;
import com.fidelite.enums.TransactionType;
import com.fidelite.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Get paginated transaction history for a card, newest first
    Page<Transaction> findByCardLoyaltyCardIdOrderByCreatedAtDesc(UUID cardId, Pageable pageable);

    // Check if a transaction with this idempotency key already exists (prevents duplicates)
    boolean existsByIdempotencyKey(String idempotencyKey);

    // Fetch a transaction by its idempotency key (used to return the original response on retry)
    Optional<Transaction> findByIdempotencyKey(String idempotencyKey);

    // Returns paginated transactions for all cards belonging to a merchant, newest first
    Page<Transaction> findByCardCustomerMerchantIdMerchantOrderByCreatedAtDesc(UUID merchantId, Pageable pageable);

    // Returns each product's EARN transaction count for a merchant, sorted descending
    @Query("""
        SELECT new com.fidelite.dto.responseDTO.ProductSalesDTO(p.name, COUNT(t))
        FROM Transaction t JOIN t.products p
        WHERE t.card.customer.merchant.idMerchant = :merchantId
          AND t.type = com.fidelite.enums.TransactionType.EARN
        GROUP BY p.idProduct, p.name
        ORDER BY COUNT(t) DESC
        """)
    List<ProductSalesDTO> countProductSalesByMerchant(@Param("merchantId") UUID merchantId);

    // Sum all points of a given type issued by a merchant within a date range (used for settlement)
    @Query("""
        SELECT COALESCE(SUM(t.points), 0) FROM Transaction t
        WHERE t.card.customer.merchant.idMerchant = :merchantId
          AND t.type = :type
          AND t.createdAt BETWEEN :start AND :end
        """)
    long sumPointsByMerchantAndTypeAndPeriod(@Param("merchantId") UUID merchantId,
                                             @Param("type") TransactionType type,
                                             @Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end);
}
