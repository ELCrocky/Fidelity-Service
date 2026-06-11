package com.fidelite.dto.requests;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RedemptionRequestDTO {

    @NotNull
    private UUID cardId;

    @NotNull
    private UUID rewardId;

    private String idempotencyKey;
}
