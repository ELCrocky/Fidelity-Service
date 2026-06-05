package com.fidelite.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fidelite.models.LoyaltyCard;

@Repository
public interface LoyaltyCardRepository extends JpaRepository<LoyaltyCard, UUID> {

    // Look up a card by its EAN-13 barcode (used when scanning at POS)
    Optional<LoyaltyCard> findByBarcodeEan13(String barcodeEan13);

    // Get all cards registered to a specific customer
    List<LoyaltyCard> findByCustomerCustomerId(UUID customerId);

    // Check if a barcode is already taken before issuing a new card
    boolean existsByBarcodeEan13(String barcodeEan13);
}
