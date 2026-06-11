package com.fidelite.endpoints;

import com.fidelite.dto.requests.RedemptionRequestDTO;
import com.fidelite.dto.responseDTO.RedemptionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// REST contract for reward redemption operations.
public interface RedemptionEndpoint {

    // Redeems a reward against a loyalty card, deducting the required points.
    @Operation
    RedemptionResponseDTO redeem(RedemptionRequestDTO request);

    // Returns the paginated redemption history for the given loyalty card.
    @Operation
    Page<RedemptionResponseDTO> byCard(UUID cardId, Pageable pageable);
}
