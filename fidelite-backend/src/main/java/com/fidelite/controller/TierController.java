package com.fidelite.controller;

import com.fidelite.dto.requests.TierRequestDTO;
import com.fidelite.dto.responseDTO.TierResponseDTO;
import com.fidelite.endpoints.TierEndpoint;
import com.fidelite.service.TierService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// REST controller for loyalty tier management per merchant.
@RestController
@RequestMapping("/api/tiers")
@RequiredArgsConstructor
public class TierController implements TierEndpoint {
    private final TierService tierService;

    // Returns all tiers defined for the given merchant.
    @Override
    @GetMapping
    public List<TierResponseDTO> byMerchant(@RequestParam UUID merchantId) {
        return tierService.getByMerchant(merchantId);
    }

    // Creates a new loyalty tier for the given merchant.
    @Override
    @PostMapping
    public TierResponseDTO create(@RequestBody @Valid TierRequestDTO request) {
        return tierService.create(request);
    }

    // Deletes the tier with the given ID.
    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        tierService.delete(id);
    }
}
