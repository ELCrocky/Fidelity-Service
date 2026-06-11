package com.fidelite.controller;

import com.fidelite.dto.requests.LoyaltyCardRequestDTO;
import com.fidelite.dto.responseDTO.LoyaltyCardResponseDTO;
import com.fidelite.endpoints.LoyaltyCardEndpoint;
import com.fidelite.service.LoyaltyCardService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// REST controller for loyalty card management and barcode lookup.
@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class LoyaltyCardController implements LoyaltyCardEndpoint {
    private final LoyaltyCardService loyaltyCardService;

    // Returns a loyalty card by its UUID.
    @Override
    @GetMapping("/{id}")
    public LoyaltyCardResponseDTO byId(@PathVariable UUID id) {
        return loyaltyCardService.getById(id);
    }

    // Kiosk barcode scan -> the heart of the system.
    @Override
    @GetMapping("/barcode/{barcode}")
    public LoyaltyCardResponseDTO byBarcode(@PathVariable String barcode) {
        return loyaltyCardService.getByBarcode(barcode);
    }

    // Returns all loyalty cards belonging to the given customer.
    @Override
    @GetMapping("/customer/{customerId}")
    public List<LoyaltyCardResponseDTO> byCustomer(@PathVariable UUID customerId) {
        return loyaltyCardService.getByCustomer(customerId);
    }

    // Creates a new loyalty card, generating a barcode if none is provided.
    @Override
    @PostMapping
    public LoyaltyCardResponseDTO create(@RequestBody @Valid LoyaltyCardRequestDTO request) {
        return loyaltyCardService.create(request);
    }

    // Deletes the loyalty card with the given UUID.
    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        loyaltyCardService.delete(id);
    }
}
