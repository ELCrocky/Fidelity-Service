package com.fidelite.endpoints;

import com.fidelite.dto.requests.ProductRequestDTO;
import com.fidelite.dto.responseDTO.ProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;

// REST contract for product management operations.
public interface ProductEndpoint {

    // Returns all products belonging to the given merchant.
     @Operation
    List<ProductResponseDTO> byMerchant(UUID merchantId);

    // Creates a new product from the given request.
    @Operation
    ProductResponseDTO create(ProductRequestDTO request);

    // Deletes the product with the given ID.
    @Operation
    void delete(Long id);
}
