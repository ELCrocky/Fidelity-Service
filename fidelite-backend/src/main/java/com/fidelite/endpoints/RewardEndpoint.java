package com.fidelite.endpoints;

import com.fidelite.dto.requests.RewardRequestDTO;
import com.fidelite.dto.responseDTO.RewardResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;

// REST contract for reward catalogue management operations.
public interface RewardEndpoint {

    // Returns all rewards offered by the given merchant.
    @Operation
    List<RewardResponseDTO> byMerchant(UUID merchantId);

    // Returns rewards the customer can afford given their current point balance.
    @Operation
    List<RewardResponseDTO> affordable(UUID merchantId, int balance);

    // Creates a new reward from the given request.
    @Operation
    RewardResponseDTO create(RewardRequestDTO request);

    // Deletes the reward with the given UUID.
    @Operation
    void delete(UUID id);
}
