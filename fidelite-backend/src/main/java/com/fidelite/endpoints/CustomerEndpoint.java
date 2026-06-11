package com.fidelite.endpoints;

import com.fidelite.dto.requests.CustomerRequestDTO;
import com.fidelite.dto.responseDTO.CustomerResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// REST contract for customer management operations scoped to a merchant.
public interface CustomerEndpoint {

    // Returns a paginated list of customers belonging to the given merchant.
    @Operation
    Page<CustomerResponseDTO> byMerchant(UUID merchantId, Pageable pageable);

    // Returns a single customer by ID, validating it belongs to the given merchant.
    @Operation
    CustomerResponseDTO byId(UUID merchantId, UUID id);

    // Creates a new customer under the specified merchant.
    @Operation
    CustomerResponseDTO create(UUID merchantId, CustomerRequestDTO request);

    // Deletes the customer with the given UUID.
    @Operation
    void delete(UUID id);

}
