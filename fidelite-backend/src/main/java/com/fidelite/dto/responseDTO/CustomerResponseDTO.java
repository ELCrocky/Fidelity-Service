package com.fidelite.dto.responseDTO;

import java.time.LocalDate;
import java.util.UUID;


import lombok.Data;

@Data
public class CustomerResponseDTO {

private UUID id;

private String nom;

private String prenom;

private String email;

private String phone;

private LocalDate dateNaissance;

private boolean markettingConsent;

private String tierName;

}
