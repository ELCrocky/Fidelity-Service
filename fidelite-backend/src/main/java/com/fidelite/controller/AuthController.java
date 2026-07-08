package com.fidelite.controller;

import com.fidelite.dto.requests.LoginRequestDTO;
import com.fidelite.dto.responseDTO.LoginResponseDTO;
import com.fidelite.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Authentication endpoints for branch staff and clearing users.
@Tag(name = "Auth", description = "Authentication")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Branch staff / kiosk login")
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Valid LoginRequestDTO request) {
        return authService.loginAppUser(request);
    }

    @Operation(summary = "Clearing staff login")
    @PostMapping("/clearing/login")
    public LoginResponseDTO clearingLogin(@RequestBody @Valid LoginRequestDTO request) {
        return authService.loginClearingUser(request);
    }
}
