package com.fidelite.dto.responseDTO;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class SettlementLineResponseDTO {

    private Long id;
    private UUID merchantId;
    private String merchantName;
    private long pointsIssued;
    private long pointsRedeemed;
    private long netPoints;
    private BigDecimal netAmount;

}
