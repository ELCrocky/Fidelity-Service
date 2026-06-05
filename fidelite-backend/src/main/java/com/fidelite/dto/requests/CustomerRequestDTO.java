package com.fidelite.dto.requests;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRequestDTO {

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @Email
    private String email;

    private String phone;

    private LocalDate dateNaissance;

    private boolean marketingConsent;
}
