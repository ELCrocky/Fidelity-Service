package com.fidelite.dto.responseDTO;

import java.util.UUID;

import lombok.Data;

@Data
public class ClearingUserResponseDTO {

    private UUID id;
    private String email;
    private UUID clearingCompanyId;

}
