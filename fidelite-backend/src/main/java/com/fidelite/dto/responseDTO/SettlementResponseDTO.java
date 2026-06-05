package com.fidelite.dto.responseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class SettlementResponseDTO {

    private Long id;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private long pointsIssued;
    private long pointsRedeemed;
    private BigDecimal netAmount;
    private BigDecimal comission;
    private LocalDateTime createdAt;
    private List<SettlementLineResponseDTO> lines;
}
