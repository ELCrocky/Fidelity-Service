package com.fidelite.component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.ClearingUser;
import com.fidelite.repository.ClearingUserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// Data-access component for ClearingUser entities.
@Component
@RequiredArgsConstructor
public class ClearingUserComponent {

    private final ClearingUserRepository clearingUserRepository;

    public ClearingUser save(ClearingUser user) {
        return clearingUserRepository.save(user);
    }

    // Finds a clearing user by email; used for login lookup.
    public ClearingUser findByEmail(String email) throws NotFoundElementException {
        return clearingUserRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundElementException(
                        String.format("clearing user with email [%s] not found", email)));
    }

    // Returns true if an account with the given email already exists.
    public boolean existsByEmail(String email) {
        return clearingUserRepository.existsByEmail(email);
    }

    public void deleteById(UUID id) {
        clearingUserRepository.deleteById(id);
    }
}
