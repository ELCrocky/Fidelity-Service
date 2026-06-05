package com.fidelite.repository;

import com.fidelite.models.ClearingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClearingUserRepository extends JpaRepository<ClearingUser, UUID> {

    // Find a clearing company staff member by email (used for login lookup)
    Optional<ClearingUser> findByEmail(String email);

    // Check if an email is already registered (used for duplicate validation)
    boolean existsByEmail(String email);
}
