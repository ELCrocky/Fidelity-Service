package com.fidelite.component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.AppUser;
import com.fidelite.repository.AppUserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// Data-access component for AppUser entities.
@Component
@RequiredArgsConstructor
public class AppUserComponent {

    private final AppUserRepository appUserRepository;

    public AppUser save(AppUser user) {
        return appUserRepository.save(user);
    }

    public AppUser findById(UUID id) throws NotFoundElementException {
        return appUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(
                        String.format("app user with id [%s] not found", id)));
    }

    // Finds a user by email; used for login lookup.
    public AppUser findByEmail(String email) throws NotFoundElementException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundElementException(
                        String.format("app user with email [%s] not found", email)));
    }

    // Returns true if an account with the given email already exists.
    public boolean existsByEmail(String email) {
        return appUserRepository.existsByEmail(email);
    }

    // Returns all staff users belonging to the given merchant.
    public List<AppUser> findByMerchantId(UUID merchantId) {
        return appUserRepository.findByMerchantIdMerchant(merchantId);
    }

    public void deleteById(UUID id) {
        appUserRepository.deleteById(id);
    }
}
