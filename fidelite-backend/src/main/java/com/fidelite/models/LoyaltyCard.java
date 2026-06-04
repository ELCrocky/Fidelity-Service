package com.fidelite.models;

import java.util.UUID;

import org.hibernate.annotations.Collate;

import com.fidelite.enums.CardStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LoyaltyCard {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID loyaltyCardId;

    @NotBlank
    @Column(nullable = false, length = 13, unique = true)
    private String barcodeEan13;

    @Min(0)
    @Column(nullable = false)
    private int pointsBalance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardStatus status = CardStatus.ACTIVE;

    //relations
}
