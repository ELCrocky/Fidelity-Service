package com.fidelite.endpoints;

import com.fidelite.dto.requests.TierRequestDTO;
import com.fidelite.dto.responseDTO.TierResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;

// REST contract for loyalty tier management operations.
public interface TierEndpoint {

    // Returns all tiers defined for the given merchant.
    @Operation
    List<TierResponseDTO> byMerchant(UUID merchantId);

    // Creates a new tier from the given request.
    @Operation
    TierResponseDTO create(TierRequestDTO request);

    // Deletes the tier with the given ID.
    @Operation
    void delete(Long id);
}
