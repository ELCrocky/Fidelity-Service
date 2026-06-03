package com.fidelite.dto.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FideliteRequestDTO {

    private String nom;
    private String prenom;
    private String mailClient;
    private String password;

}
