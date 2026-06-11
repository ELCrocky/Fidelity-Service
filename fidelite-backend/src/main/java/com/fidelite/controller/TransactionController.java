package com.fidelite.controller;

import com.fidelite.dto.requests.TransactionRequestDTO;
import com.fidelite.dto.responseDTO.TransactionResponseDTO;
import com.fidelite.endpoints.TransactionEndpoint;
import com.fidelite.service.TransactionService;
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

// REST controller for point transactions (earn/redeem/adjust/expire).
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController implements TransactionEndpoint {
    private final TransactionService transactionService;

    // Point earning/spending -> core business logic (tier multiplier, idempotency) is in the service.
    @Override
    @PostMapping
    public TransactionResponseDTO create(@RequestBody @Valid TransactionRequestDTO request) {
        return transactionService.create(request);
    }

    // Returns the paginated transaction history for the given loyalty card.
    @Override
    @GetMapping("/card/{cardId}")
    public Page<TransactionResponseDTO> byCard(@PathVariable UUID cardId, Pageable pageable) {
        return transactionService.getByCard(cardId, pageable);
    }
}
