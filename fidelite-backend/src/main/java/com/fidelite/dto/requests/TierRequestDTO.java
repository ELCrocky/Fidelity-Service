package com.fidelite.dto.requests;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TierRequestDTO {

    @NotBlank
    private String name;

    @Min(0)
    private int minPoints;

    @NotNull
    private BigDecimal multiplier;

    @NotNull
    private UUID merchantId;

}
