package com.fidelite.controller;

import com.fidelite.dto.requests.MerchantRequestDTO;
import com.fidelite.dto.responseDTO.MerchantResponseDTO;
import com.fidelite.dto.responseDTO.SettlementLineResponseDTO;
import com.fidelite.endpoints.MerchantEndpoint;
import com.fidelite.service.MerchantService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// REST controller for merchant management and settlement history queries.
@RestController
@RequestMapping("/api/merchants")
@RequiredArgsConstructor
public class MerchantController implements MerchantEndpoint {

    private final MerchantService merchantService;

    // Returns all merchants.
    @Override
    @GetMapping
    public List<MerchantResponseDTO> all() {
        return merchantService.getAll();
    }

    // Returns a merchant by its UUID.
    @Override
    @GetMapping("/{id}")
    public MerchantResponseDTO byId(@PathVariable UUID id) {
        return merchantService.getById(id);
    }

    // Returns all merchants belonging to the given settlement pool.
    @Override
    @GetMapping("/pool/{poolId}")
    public List<MerchantResponseDTO> byPool(@PathVariable UUID poolId) {
        return merchantService.getByPool(poolId);
    }

    // Creates a new merchant and generates its API key.
    @Override
    @PostMapping
    public MerchantResponseDTO create(@RequestBody @Valid MerchantRequestDTO request) {
        return merchantService.create(request);
    }

    // Deletes the merchant with the given UUID.
    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        merchantService.delete(id);
    }

    // Returns the paginated settlement line history for the given merchant.
    @Override
    @GetMapping("/{id}/settlement-history")
    public Page<SettlementLineResponseDTO> settlementHistory(@PathVariable UUID id, Pageable pageable) {
        return merchantService.getSettlementHistory(id, pageable);
    }
}
