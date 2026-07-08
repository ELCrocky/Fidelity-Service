package com.fidelite.endpoints;

import com.fidelite.dto.requests.AppUserRequestDTO;
import com.fidelite.dto.responseDTO.AppUserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;

// Swagger contract for staff user management endpoints.
@Tag(name = "AppUser", description = "Branch staff user management")
public interface AppUserEndpoint {

    @Operation(summary = "List all staff users for a merchant")
    List<AppUserResponseDTO> byMerchant(UUID merchantId);

    @Operation(summary = "Register a new staff user (password is hashed)")
    AppUserResponseDTO register(AppUserRequestDTO request);

    @Operation(summary = "Delete a staff user")
    void delete(UUID id);
}
