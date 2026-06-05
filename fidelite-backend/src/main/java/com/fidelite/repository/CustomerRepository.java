package com.fidelite.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fidelite.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    // Get a paginated list of all customers belonging to a specific merchant
    Page<Customer> findByMerchantIdMerchant(UUID merchantId, Pageable pageable);

    // Find a customer by ID scoped to a merchant (prevents cross-tenant access)
    Optional<Customer> findByCustomerIdAndMerchantIdMerchant(UUID customerId, UUID merchantId);

    // Find a customer by email within a specific merchant (used for duplicate check)
    Optional<Customer> findByMerchantIdMerchantAndEmail(UUID merchantId, String email);
}
