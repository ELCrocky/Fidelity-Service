package com.fidelite.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fidelite.component.CustomerComponent;
import com.fidelite.component.MerchantComponent;
import com.fidelite.dto.requests.CustomerRequestDTO;
import com.fidelite.dto.responseDTO.CustomerResponseDTO;
import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.exceptions.rest.BadRequestRestException;
import com.fidelite.exceptions.rest.NotFoundRestException;
import com.fidelite.mappers.CustomerMapper;
import com.fidelite.models.Customer;

import lombok.RequiredArgsConstructor;

// Service layer for customer management scoped to a merchant.
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerComponent customerComponent;
    private final MerchantComponent merchantComponent;
    private final CustomerMapper customerMapper;

    // Returns a paginated list of customers for the given merchant.
    public Page<CustomerResponseDTO> getByMerchant(UUID merchantId, Pageable pageable) {
        return customerComponent.findByMerchantId(merchantId, pageable)
                .map(customerMapper::toResponse);
    }

    // Returns a customer by ID scoped to a merchant, translating not-found to a REST exception.
    public CustomerResponseDTO getById(UUID merchantId, UUID id) {
        try {
            return customerMapper.toResponse(
                    customerComponent.findByIdAndMerchantId(id, merchantId));
        } catch (NotFoundElementException e) {
            throw new NotFoundRestException(e.getMessage());
        }
    }

    // Creates a customer under the specified merchant, resolving the merchant entity first.
    public CustomerResponseDTO create(UUID merchantId, CustomerRequestDTO request) {
        try {
            Customer customer = customerMapper.toEntity(request);
            customer.setMerchant(merchantComponent.findById(merchantId));
            return customerMapper.toResponse(customerComponent.save(customer));
        } catch (NotFoundElementException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    public void delete(UUID id) {
        customerComponent.deleteById(id);
    }
}
