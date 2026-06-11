package com.fidelite.service;

import com.fidelite.component.CustomerComponent;
import com.fidelite.component.LoyaltyCardComponent;
import com.fidelite.dto.requests.LoyaltyCardRequestDTO;
import com.fidelite.dto.responseDTO.LoyaltyCardResponseDTO;
import com.fidelite.exceptions.rest.BadRequestRestException;
import com.fidelite.exceptions.rest.NotFoundRestException;
import com.fidelite.exceptions.technical.NotFoundElementException;
import com.fidelite.mappers.LoyaltyCardMapper;
import com.fidelite.models.LoyaltyCard;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// Service layer for loyalty card management, including barcode generation.
@Service
@RequiredArgsConstructor
public class LoyaltyCardService {
    private final LoyaltyCardComponent loyaltyCardComponent;
    private final CustomerComponent customerComponent;
    private final LoyaltyCardMapper loyaltyCardMapper;

    // Returns a loyalty card by UUID, translating not-found to a REST exception.
    public LoyaltyCardResponseDTO getById(UUID id) {
        try {
            return loyaltyCardMapper.toResponse(loyaltyCardComponent.findById(id));
        } catch (NotFoundElementException e) {
            throw new NotFoundRestException(e.getMessage());
        }
    }

    // Kiosk barcode scan -> the heart of the system.
    public LoyaltyCardResponseDTO getByBarcode(String barcode) {
        try {
            return loyaltyCardMapper.toResponse(loyaltyCardComponent.findByBarcode(barcode));
        } catch (NotFoundElementException e) {
            throw new NotFoundRestException(e.getMessage());
        }
    }

    // Returns all loyalty cards belonging to the given customer.
    public List<LoyaltyCardResponseDTO> getByCustomer(UUID customerId) {
        return loyaltyCardComponent.findByCustomerId(customerId)
                .stream()
                .map(loyaltyCardMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Creates a loyalty card; resolves the owner, validates or auto-generates the barcode.
    public LoyaltyCardResponseDTO create(LoyaltyCardRequestDTO request) {
        try {
            LoyaltyCard card = loyaltyCardMapper.toEntity(request);
            // Resolve the card owner.
            card.setCustomer(customerComponent.findById(request.getCustomerId()));
            // If a barcode is supplied check for collision; otherwise generate one.
            if (request.getBarcodeEan13() != null && !request.getBarcodeEan13().isBlank()) {
                if (loyaltyCardComponent.existsByBarcode(request.getBarcodeEan13())) {
                    throw new BadRequestRestException(
                            String.format("barcode [%s] already in use", request.getBarcodeEan13()));
                }
                card.setBarcodeEan13(request.getBarcodeEan13());
            } else {
                card.setBarcodeEan13(generateUniqueBarcode());
            }
            return loyaltyCardMapper.toResponse(loyaltyCardComponent.save(card));
        } catch (NotFoundElementException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    public void delete(UUID id) {
        loyaltyCardComponent.deleteById(id);
    }

    // Generates a unique 13-digit barcode, retrying on collision.
    private String generateUniqueBarcode() {
        String barcode;
        do {
            barcode = String.valueOf((long) (Math.random() * 9_000_000_000_000L) + 1_000_000_000_000L);
        } while (loyaltyCardComponent.existsByBarcode(barcode));
        return barcode;
    }
}
