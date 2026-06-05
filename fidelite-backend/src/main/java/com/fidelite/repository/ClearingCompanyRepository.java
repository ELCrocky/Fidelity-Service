package com.fidelite.repository;

import com.fidelite.models.ClearingCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClearingCompanyRepository extends JpaRepository<ClearingCompany, UUID> {

    // Find a clearing company by its display name
    Optional<ClearingCompany> findByName(String name);
}
