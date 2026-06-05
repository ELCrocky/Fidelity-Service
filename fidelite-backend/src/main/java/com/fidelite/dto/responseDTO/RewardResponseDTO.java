package com.fidelite.dto.responseDTO;

import java.util.UUID;

import lombok.Data;

@Data
public class RewardResponseDTO {

    private UUID id;
    private String name;
    private int costPoints;

}
