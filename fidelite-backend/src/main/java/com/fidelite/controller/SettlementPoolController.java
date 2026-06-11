package com.fidelite.controller;

import com.fidelite.dto.requests.SettlementPoolRequestDTO;
import com.fidelite.dto.responseDTO.SettlementPoolResponseDTO;
import com.fidelite.endpoints.SettlementPoolEndpoint;
import com.fidelite.service.SettlementPoolService;
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

// REST controller for settlement pool management.
@RestController
@RequestMapping("/api/pools")
@RequiredArgsConstructor
public class SettlementPoolController implements SettlementPoolEndpoint {
    private final SettlementPoolService settlementPoolService;

    // Returns a settlement pool by its UUID.
    @Override
    @GetMapping("/{id}")
    public SettlementPoolResponseDTO byId(@PathVariable UUID id) {
        return settlementPoolService.getById(id);
    }

    // Returns all settlement pools for the given clearing company.
    @Override
    @GetMapping
    public List<SettlementPoolResponseDTO> byClearingCompany(@RequestParam UUID clearingCompanyId) {
        return settlementPoolService.getByClearingCompany(clearingCompanyId);
    }

    // Creates a new settlement pool linked to a clearing company.
    @Override
    @PostMapping
    public SettlementPoolResponseDTO create(@RequestBody @Valid SettlementPoolRequestDTO request) {
        return settlementPoolService.create(request);
    }

    // Deletes the settlement pool with the given UUID.
    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        settlementPoolService.delete(id);
    }
}
