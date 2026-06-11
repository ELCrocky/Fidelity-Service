package com.fidelite.component;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.LoyaltyCard;
import com.fidelite.repository.LoyaltyCardRepository;

import lombok.RequiredArgsConstructor;

// Data-access component for LoyaltyCard entities.
@Component
@RequiredArgsConstructor
public class LoyaltyCardComponent {

    private final LoyaltyCardRepository loyaltyCardRepository;

    public LoyaltyCard save(LoyaltyCard card) {
        return loyaltyCardRepository.save(card);
    }

    // Finds a loyalty card by UUID, throwing a typed exception if absent.
    public LoyaltyCard findById(UUID id) throws NotFoundElementException {
        return loyaltyCardRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(
                        String.format("loyalty card with id [%s] not found", id)));
    }

    // Kiosk barcode scan -> the heart of the system.
    public LoyaltyCard findByBarcode(String barcode) throws NotFoundElementException {
        return loyaltyCardRepository.findByBarcodeEan13(barcode)
                .orElseThrow(() -> new NotFoundElementException(
                        String.format("loyalty card with barcode [%s] not found", barcode)));
    }

    // Returns all loyalty cards belonging to the given customer.
    public List<LoyaltyCard> findByCustomerId(UUID customerId) {
        return loyaltyCardRepository.findByCustomerCustomerId(customerId);
    }

    // Returns true if a card with the given barcode already exists.
    public boolean existsByBarcode(String barcode) {
        return loyaltyCardRepository.existsByBarcodeEan13(barcode);
    }

    public void deleteById(UUID id) {
        loyaltyCardRepository.deleteById(id);
    }
}
