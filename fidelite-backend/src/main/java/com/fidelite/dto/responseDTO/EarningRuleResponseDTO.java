package com.fidelite.dto.responseDTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EarningRuleResponseDTO {

private Long id;
private String condition;
private int pointsAwarded;
private LocalDate validFrom;
private LocalDate validTo;

}
