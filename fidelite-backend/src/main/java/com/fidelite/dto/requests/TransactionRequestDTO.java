package com.fidelite.dto.requests;

import java.util.List;
import java.util.UUID;

import com.fidelite.enums.TransactionType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionRequestDTO {

    private int points;

    @NotNull
    private TransactionType type;

    @NotNull
    private UUID cardId;

    private List<Long> productsId;

    private String idempotencyKey;
    
}
