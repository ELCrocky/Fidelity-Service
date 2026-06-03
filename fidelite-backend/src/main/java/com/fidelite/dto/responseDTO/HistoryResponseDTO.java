package com.fidelite.dto.responseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class HistoryResponseDTO {
    private int points;
    private int transactionId;
    private String transactionDate;
    private String transactionType;
}