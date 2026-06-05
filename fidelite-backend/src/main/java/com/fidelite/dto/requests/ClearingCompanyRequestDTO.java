package com.fidelite.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClearingCompanyRequestDTO {

    @NotBlank
    private String name;
}
