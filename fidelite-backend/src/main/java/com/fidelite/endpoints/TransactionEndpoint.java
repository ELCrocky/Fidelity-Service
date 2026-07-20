package com.fidelite.endpoints;

import com.fidelite.dto.requests.TransactionRequestDTO;
import com.fidelite.dto.responseDTO.ProductSalesDTO;
import com.fidelite.dto.responseDTO.TransactionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
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

    // Returns paginated transactions for all cards of a merchant.
    @Operation
    Page<TransactionResponseDTO> byMerchant(UUID merchantId, Pageable pageable);

    // Returns product sales counts for a merchant, sorted by most sold.
    @Operation
    List<ProductSalesDTO> productSalesByMerchant(UUID merchantId);
}
