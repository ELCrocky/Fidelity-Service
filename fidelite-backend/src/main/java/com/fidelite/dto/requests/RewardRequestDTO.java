package com.fidelite.dto.requests;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RewardRequestDTO {

@NotBlank
private String name;

@Min(1)
private int costPoints;

@NotNull
private UUID merchantId;

}
