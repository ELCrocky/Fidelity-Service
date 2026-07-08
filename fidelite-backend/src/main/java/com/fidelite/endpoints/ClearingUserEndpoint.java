package com.fidelite.endpoints;

import com.fidelite.dto.requests.ClearingUserRequestDTO;
import com.fidelite.dto.responseDTO.ClearingUserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;

// Swagger contract for clearing staff user management endpoints.
@Tag(name = "ClearingUser", description = "Clearing company staff management")
public interface ClearingUserEndpoint {

    @Operation(summary = "Register a new clearing staff user (password is hashed)")
    ClearingUserResponseDTO register(ClearingUserRequestDTO request);

    @Operation(summary = "Delete a clearing staff user")
    void delete(UUID id);
}
