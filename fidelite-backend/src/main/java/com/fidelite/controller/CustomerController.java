package com.fidelite.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fidelite.dto.requests.CustomerRequestDTO;
import com.fidelite.dto.responseDTO.CustomerResponseDTO;
import com.fidelite.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// REST controller for customer management scoped to a merchant.
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // Returns a paginated list of customers for the given merchant.
    @GetMapping
    public Page<CustomerResponseDTO> byMerchant(@RequestParam UUID merchantId, Pageable pageable) {
        return customerService.getByMerchant(merchantId, pageable);
    }

    // Returns a customer by ID, scoped to the given merchant.
    @GetMapping("/{id}")
    public CustomerResponseDTO byId(@RequestParam UUID merchantId, @PathVariable UUID id) {
        return customerService.getById(merchantId, id);
    }

    // Creates a new customer under the specified merchant.
    @PostMapping
    public CustomerResponseDTO create(@RequestParam UUID merchantId,
                                      @RequestBody @Valid CustomerRequestDTO request) {
        return customerService.create(merchantId, request);
    }

    // Deletes a customer and returns 204 No Content.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
