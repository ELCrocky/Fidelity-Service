package com.fidelite.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;    // JWT — send as Authorization: Bearer <token> on subsequent requests
    private String userType; // "APP_USER" or "CLEARING_USER"
    private String role;     // e.g. MERCHANT_ADMIN, STAFF, CLEARING
}
