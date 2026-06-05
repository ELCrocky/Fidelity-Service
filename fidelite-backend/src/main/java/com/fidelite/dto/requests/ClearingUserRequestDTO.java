package com.fidelite.dto.requests;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClearingUserRequestDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private UUID clearingCompany;

}
