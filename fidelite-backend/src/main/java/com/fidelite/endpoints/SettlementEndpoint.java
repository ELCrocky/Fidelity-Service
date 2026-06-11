package com.fidelite.endpoints;

import com.fidelite.dto.requests.SettlementRequestDTO;
import com.fidelite.dto.responseDTO.SettlementResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// REST contract for triggering and querying period-end settlements.
public interface SettlementEndpoint {

    // Runs a period-end settlement calculation for the given pool and period.
    @Operation
    SettlementResponseDTO run(SettlementRequestDTO request);

    // Returns the paginated settlement history for the given settlement pool.
    @Operation
    Page<SettlementResponseDTO> byPool(UUID poolId, Pageable pageable);
}
