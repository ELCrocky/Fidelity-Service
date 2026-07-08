package com.fidelite.service;

import com.fidelite.component.AppUserComponent;
import com.fidelite.component.MerchantComponent;
import com.fidelite.dto.requests.AppUserRequestDTO;
import com.fidelite.dto.responseDTO.AppUserResponseDTO;
import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.exceptions.rest.BadRequestRestException;
import com.fidelite.mappers.AppUserMapper;
import com.fidelite.models.AppUser;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Service layer for staff user registration and management.
@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserComponent appUserComponent;
    private final MerchantComponent merchantComponent;
    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;

    // Returns all staff users belonging to the given merchant.
    public List<AppUserResponseDTO> getByMerchant(UUID merchantId) {
        return appUserComponent.findByMerchantId(merchantId)
                .stream()
                .map(appUserMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Registers a new staff user; hashes the password before persisting.
    public AppUserResponseDTO register(AppUserRequestDTO request) {
        try {
            if (appUserComponent.existsByEmail(request.getEmail())) {
                throw new BadRequestRestException(
                        String.format("email [%s] already in use", request.getEmail()));
            }
            AppUser user = appUserMapper.toEntity(request);
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            user.setMerchant(merchantComponent.findById(request.getMerchantId()));
            return appUserMapper.toResponse(appUserComponent.save(user));
        } catch (NotFoundElementException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    public void delete(UUID id) {
        appUserComponent.deleteById(id);
    }
}
