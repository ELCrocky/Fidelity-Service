package com.fidelite.dto.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class HistoryRequestDTO {

    private int points;
    private String transactionType;
    private List<String> products;

}
