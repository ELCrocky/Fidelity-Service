package com.fidelite.dto.requests;

import java.util.UUID;

import com.fidelite.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppUserRequestDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private Role role;

    @NotNull
    private UUID merchantId;
    
}
