package com.fidelite.dto.requests;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SettlementPoolRequestDTO {
    @NotBlank
    private String name;

    @NotNull
    private BigDecimal pointValue;

    private BigDecimal commissionPerInvoice;

    @NotNull
    private UUID clearingCompanyId;
}
