package com.fidelite.dto.responseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FideliteResponseDTO {

    private String idFidelite;
    private String barcodeFidelite;
    private int pointsActuels;
    private String nom;
    private String prenom;
    private String mailClient;
    private ProductResponseDTO productPromotion;
    private HistoryResponseDTO transactionHistory;

}

