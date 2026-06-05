package com.fidelite.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID customerId;

    @NotBlank
    @Column(nullable = false)
    private String nom;

    @NotBlank
    @Column(nullable = false)
    private String prenom;

    @Email
    private String email;

    private String phone;

    private LocalDate dateDeNaissance; // Customer's date of birth, used for birthday promotions

    // Whether the customer agreed to receive marketing communications
    @Builder.Default
    @Column(nullable = false)
    private boolean marketingConsent = false;

    // The merchant that enrolled this customer
    @ManyToOne(optional = false)
    @JoinColumn(name = "merchant_ide", nullable = false)
    private Merchant merchant;

    // Current loyalty tier the customer belongs to (null = no tier yet)
    @ManyToOne
    @JoinColumn(name = "tier_id")
    private Tier tier;

    // All loyalty cards associated with this customer
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<LoyaltyCard> cards = new ArrayList<>();
}
