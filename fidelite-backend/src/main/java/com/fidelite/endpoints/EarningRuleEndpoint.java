package com.fidelite.endpoints;

import java.util.List;
import java.util.UUID;

import com.fidelite.dto.requests.EarningRuleRequestDTO;
import com.fidelite.dto.responseDTO.EarningRuleResponseDTO;

import io.swagger.v3.oas.annotations.Operation;

// REST contract for earning rule management operations.
public interface EarningRuleEndpoint {

    // Returns all earning rules for the given merchant.
    @Operation
    List<EarningRuleResponseDTO> byMerchant(UUID merchantId);

    // Returns only currently active earning rules for the given merchant.
    @Operation
    List<EarningRuleResponseDTO> activeByMerchant(UUID merchantId);

    // Creates a new earning rule from the given request.
    @Operation
    EarningRuleResponseDTO create(EarningRuleRequestDTO request);

    // Deletes the earning rule with the given ID.
    @Operation
    void delete(Long id);
}
