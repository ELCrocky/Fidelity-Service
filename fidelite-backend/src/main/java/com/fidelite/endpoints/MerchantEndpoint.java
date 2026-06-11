package com.fidelite.endpoints;

import com.fidelite.dto.requests.MerchantRequestDTO;
import com.fidelite.dto.responseDTO.MerchantResponseDTO;
import com.fidelite.dto.responseDTO.SettlementLineResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

// REST contract for merchant management and settlement history queries.
public interface MerchantEndpoint {

    // Returns all merchants.
    @Operation
    List<MerchantResponseDTO> all();

    // Returns a merchant by its UUID.
    @Operation
    MerchantResponseDTO byId(UUID id);

    // Returns all merchants belonging to the given settlement pool.
    @Operation
    List<MerchantResponseDTO> byPool(UUID poolId);

    // Creates a new merchant from the given request.
    @Operation
    MerchantResponseDTO create(MerchantRequestDTO request);

    // Deletes the merchant with the given UUID.
    @Operation
    void delete(UUID id);

    // Returns the paginated settlement line history for the given merchant.
    @Operation
    Page<SettlementLineResponseDTO> settlementHistory(UUID id, Pageable pageable);
}
