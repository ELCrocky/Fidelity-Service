package com.fidelite.endpoints;

import com.fidelite.dto.requests.TransactionRequestDTO;
import com.fidelite.dto.responseDTO.TransactionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// REST contract for point transaction operations (earn, redeem, adjust, expire).
public interface TransactionEndpoint {

    // Creates a new point transaction (earn/redeem/adjust/expire) for a loyalty card.
    @Operation
    TransactionResponseDTO create(TransactionRequestDTO request);

    // Returns the paginated transaction history for the given loyalty card.
    @Operation
    Page<TransactionResponseDTO> byCard(UUID cardId, Pageable pageable);
}
