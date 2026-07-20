package com.fidelite.dto.responseDTO;

import java.time.LocalDateTime;
import java.util.List;

import com.fidelite.enums.TransactionType;

import lombok.Data;

@Data
public class TransactionResponseDTO {

    private Long id;
    private int points;
    private TransactionType type;
    private LocalDateTime createdAt;
    private List<String> productNames;
    private String customerName;
    private String cardBarcode;

}
