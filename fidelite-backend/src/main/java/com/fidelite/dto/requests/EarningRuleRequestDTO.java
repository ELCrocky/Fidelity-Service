package com.fidelite.dto.requests;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EarningRuleRequestDTO {

    @NotBlank
    private String condition;

    @Min(0)
    private int pointsAwarded;

    private LocalDate validFrom;

    private LocalDate validTo;

    @NotNull
    private UUID merchantId;

}
