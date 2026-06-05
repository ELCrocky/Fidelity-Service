package com.fidelite.dto.requests;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SettlementRequestDTO {

    @NotNull
    private UUID poolId;

    @NotNull
    private LocalDate periodStart;

    @NotNull
    private LocalDate periodEnd;
}
