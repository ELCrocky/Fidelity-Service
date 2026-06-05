package com.fidelite.dto.responseDTO;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class SettlementPoolResponseDTO {

    private UUID id;
    private String name;
    private BigDecimal pointValue;
    private BigDecimal balance;
    private BigDecimal commissionPerInvoice;

}
