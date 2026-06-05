package com.fidelite.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fidelite.models.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    // Find a staff user by their email address (used for login lookup)
    Optional<AppUser> findByEmail(String email);

    // Check if an email is already registered (used for duplicate validation)
    boolean existsByEmail(String email);

    // Get all staff users belonging to a specific merchant
    List<AppUser> findByMerchantIdMerchant(UUID merchantId);
}
