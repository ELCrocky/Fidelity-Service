package com.fidelite.service;

import com.fidelite.component.AppUserComponent;
import com.fidelite.component.ClearingUserComponent;
import com.fidelite.config.JwtService;
import com.fidelite.dto.requests.LoginRequestDTO;
import com.fidelite.dto.responseDTO.LoginResponseDTO;
import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.exceptions.rest.BadRequestRestException;
import com.fidelite.models.AppUser;
import com.fidelite.models.ClearingUser;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Handles authentication for both staff users and clearing users.
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserComponent appUserComponent;
    private final ClearingUserComponent clearingUserComponent;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Branch staff / kiosk login. Returns a JWT on success.
    public LoginResponseDTO loginAppUser(LoginRequestDTO request) {
        try {
            AppUser user = appUserComponent.findByEmail(request.getEmail());
            if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
                throw new BadRequestRestException("invalid credentials");
            }
            String token = jwtService.generateToken(user.getEmail(), Map.of(
                    "userType", "APP_USER",
                    "role", user.getRole().name(),
                    "merchantId", user.getMerchant().getIdMerchant().toString()
            ));
            return new LoginResponseDTO(token, "APP_USER", user.getRole().name());
        } catch (NotFoundElementException e) {
            // Return the same message whether the user was not found or the password was wrong
            // to avoid leaking whether an email is registered.
            throw new BadRequestRestException("invalid credentials");
        }
    }

    // Clearing company staff login. Returns a JWT on success.
    public LoginResponseDTO loginClearingUser(LoginRequestDTO request) {
        try {
            ClearingUser user = clearingUserComponent.findByEmail(request.getEmail());
            if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
                throw new BadRequestRestException("invalid credentials");
            }
            String token = jwtService.generateToken(user.getEmail(), Map.of(
                    "userType", "CLEARING_USER",
                    "role", "CLEARING",
                    "clearingCompanyId", user.getClearingCompany().getCcId().toString()
            ));
            return new LoginResponseDTO(token, "CLEARING_USER", "CLEARING");
        } catch (NotFoundElementException e) {
            throw new BadRequestRestException("invalid credentials");
        }
    }
}
