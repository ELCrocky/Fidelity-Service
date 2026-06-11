package com.fidelite.component;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Customer;
import com.fidelite.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

// Data-access component for Customer entities.
@Component
@RequiredArgsConstructor
public class CustomerComponent {

    private final CustomerRepository customerRepository;

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    // Finds a customer by UUID, throwing a typed exception if absent.
    public Customer findById(UUID id) throws NotFoundElementException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(
                        String.format("customer with id [%s] not found", id)));
    }

    // Finds a customer by ID scoped to a specific merchant, throwing if absent.
    public Customer findByIdAndMerchantId(UUID id, UUID merchantId) throws NotFoundElementException {
        return customerRepository.findByCustomerIdAndMerchantIdMerchant(id, merchantId)
                .orElseThrow(() -> new NotFoundElementException(
                        String.format("customer with id [%s] not found for merchant [%s]", id, merchantId)));
    }

    // Returns a paginated list of customers belonging to the given merchant.
    public Page<Customer> findByMerchantId(UUID merchantId, Pageable pageable) {
        return customerRepository.findByMerchantIdMerchant(merchantId, pageable);
    }

    public void deleteById(UUID id) {
        customerRepository.deleteById(id);
    }
}
