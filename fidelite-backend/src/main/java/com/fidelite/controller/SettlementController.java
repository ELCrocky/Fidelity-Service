package com.fidelite.controller;

import com.fidelite.dto.requests.SettlementRequestDTO;
import com.fidelite.dto.responseDTO.SettlementResponseDTO;
import com.fidelite.endpoints.SettlementEndpoint;
import com.fidelite.service.SettlementService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// REST controller for triggering and querying period-end settlements.
@RestController
@RequestMapping("/api/settlements")
@RequiredArgsConstructor
public class SettlementController implements SettlementEndpoint {
    private final SettlementService settlementService;

    // Period-end reconciliation calculation -> core logic (aggregation, line generation, commission) is in the service.
    @Override
    @PostMapping
    public SettlementResponseDTO run(@RequestBody @Valid SettlementRequestDTO request) {
        return settlementService.runSettlement(request);
    }

    // Returns the paginated settlement history for the given settlement pool.
    @Override
    @GetMapping("/pool/{poolId}")
    public Page<SettlementResponseDTO> byPool(@PathVariable UUID poolId, Pageable pageable) {
        return settlementService.getByPool(poolId, pageable);
    }
}
