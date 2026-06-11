package com.fidelite.endpoints;

import com.fidelite.dto.requests.ClearingCompanyRequestDTO;
import com.fidelite.dto.responseDTO.ClearingCompanyResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;

// REST contract for clearing company management operations.
public interface ClearingCompanyEndpoint {

    // Returns all clearing companies.
    @Operation
    List<ClearingCompanyResponseDTO> all();

    // Returns a single clearing company by its UUID.
    @Operation
    ClearingCompanyResponseDTO byId(UUID id);

    // Creates a new clearing company from the given request.
    @Operation
    ClearingCompanyResponseDTO create(ClearingCompanyRequestDTO request);

    // Deletes the clearing company with the given UUID.
    @Operation
    void delete(UUID id);
}
