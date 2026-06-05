package com.fidelite.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fidelite.enums.CardStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyCard {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID loyaltyCardId;

    @NotBlank
    @Column(nullable = false, length = 13, unique = true)
    private String barcodeEan13; // EAN-13 barcode printed on the physical or digital card

    @Min(0)
    @Column(nullable = false)
    private int pointsBalance; // Current point balance on this card

    // Whether the card is usable (ACTIVE) or blocked/expired
    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardStatus status = CardStatus.ACTIVE;

    // The customer who owns this card
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // All point-earning transactions made with this card
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Transaction> transactiona = new ArrayList<>();

    // All reward redemptions made with this card
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Redemption> redemptions = new ArrayList<>();
}
