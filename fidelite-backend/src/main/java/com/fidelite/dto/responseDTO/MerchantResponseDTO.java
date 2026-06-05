package com.fidelite.dto.responseDTO;

import java.util.UUID;

import com.fidelite.enums.MerchantStatus;

import lombok.Data;

@Data
public class MerchantResponseDTO {
private UUID id;
private String name;
private String region;
private MerchantStatus status;
private UUID poolId;
}