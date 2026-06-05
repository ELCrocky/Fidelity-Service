package com.fidelite.dto.responseDTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RedemptionResponseDTO {
    private Long id;
    private String rewardName;
    private LocalDateTime redeemedAt;
}
