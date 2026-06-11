package com.fidelite.endpoints;

import com.fidelite.dto.requests.LoyaltyCardRequestDTO;
import com.fidelite.dto.responseDTO.LoyaltyCardResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;

// REST contract for loyalty card management operations.
public interface LoyaltyCardEndpoint {

    // Returns a loyalty card by its UUID.
    @Operation
    LoyaltyCardResponseDTO byId(UUID id);

    // Returns a loyalty card by its EAN-13 barcode.
    @Operation
    LoyaltyCardResponseDTO byBarcode(String barcode);

    // Returns all loyalty cards belonging to the given customer.
    @Operation
    List<LoyaltyCardResponseDTO> byCustomer(UUID customerId);

    // Creates a new loyalty card from the given request.
    @Operation
    LoyaltyCardResponseDTO create(LoyaltyCardRequestDTO request);

    // Deletes the loyalty card with the given UUID.
    @Operation
    void delete(UUID id);
}
