package com.fidelite.dto.requests;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoyaltyCardRequestDTO {

    private String barcodeEan13;

    @NotNull
    private UUID customerId;

    
}
