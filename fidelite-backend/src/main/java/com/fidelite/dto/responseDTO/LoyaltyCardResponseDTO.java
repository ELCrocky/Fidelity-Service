package com.fidelite.dto.responseDTO;

import java.util.UUID;

import com.fidelite.enums.CardStatus;

import lombok.Data;

@Data
public class LoyaltyCardResponseDTO {

    private UUID id;
    private String barcodeEan13;
    private int pointsBalance;
    private CardStatus status;
    
}
