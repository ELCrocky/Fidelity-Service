package com.fidelite.endpoints;

import com.fidelite.dto.requests.SettlementPoolRequestDTO;
import com.fidelite.dto.responseDTO.SettlementPoolResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;

// REST contract for settlement pool management operations.
public interface SettlementPoolEndpoint {

    // Returns a settlement pool by its UUID.
    @Operation
    SettlementPoolResponseDTO byId(UUID id);

    // Returns all settlement pools belonging to the given clearing company.
    @Operation
    List<SettlementPoolResponseDTO> byClearingCompany(UUID clearingCompanyId);

    // Creates a new settlement pool from the given request.
    @Operation
    SettlementPoolResponseDTO create(SettlementPoolRequestDTO request);

    // Deletes the settlement pool with the given UUID.
    @Operation
    void delete(UUID id);
}
