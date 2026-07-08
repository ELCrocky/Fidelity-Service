package com.fidelite.service;

import com.fidelite.component.ClearingCompanyComponent;
import com.fidelite.component.ClearingUserComponent;
import com.fidelite.dto.requests.ClearingUserRequestDTO;
import com.fidelite.dto.responseDTO.ClearingUserResponseDTO;
import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.exceptions.rest.BadRequestRestException;
import com.fidelite.mappers.ClearingUserMapper;
import com.fidelite.models.ClearingUser;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Service layer for clearing staff user registration and management.
@Service
@RequiredArgsConstructor
public class ClearingUserService {

    private final ClearingUserComponent clearingUserComponent;
    private final ClearingCompanyComponent clearingCompanyComponent;
    private final ClearingUserMapper clearingUserMapper;
    private final PasswordEncoder passwordEncoder;

    // Registers a new clearing staff user; hashes the password before persisting.
    public ClearingUserResponseDTO register(ClearingUserRequestDTO request) {
        try {
            if (clearingUserComponent.existsByEmail(request.getEmail())) {
                throw new BadRequestRestException(
                        String.format("email [%s] already in use", request.getEmail()));
            }
            ClearingUser user = clearingUserMapper.toEntity(request);
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            user.setClearingCompany(clearingCompanyComponent.findById(request.getClearingCompanyId()));
            return clearingUserMapper.toResponse(clearingUserComponent.save(user));
        } catch (NotFoundElementException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    public void delete(UUID id) {
        clearingUserComponent.deleteById(id);
    }
}
