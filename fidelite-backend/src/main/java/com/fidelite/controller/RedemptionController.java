package com.fidelite.controller;

import com.fidelite.dto.requests.RedemptionRequestDTO;
import com.fidelite.dto.responseDTO.RedemptionResponseDTO;
import com.fidelite.endpoints.RedemptionEndpoint;
import com.fidelite.service.RedemptionService;
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

// REST controller for reward redemption; balance check and negative-balance guard are in the service.
@RestController
@RequestMapping("/api/redemptions")
@RequiredArgsConstructor
public class RedemptionController implements RedemptionEndpoint {
    private final RedemptionService redemptionService;

    // Redemption -> balance check and negative-balance guard are handled in the service.
    @Override
    @PostMapping
    public RedemptionResponseDTO redeem(@RequestBody @Valid RedemptionRequestDTO request) {
        return redemptionService.redeem(request);
    }

    // Returns the paginated redemption history for the given loyalty card.
    @Override
    @GetMapping("/card/{cardId}")
    public Page<RedemptionResponseDTO> byCard(@PathVariable UUID cardId, Pageable pageable) {
        return redemptionService.getByCard(cardId, pageable);
    }
}
