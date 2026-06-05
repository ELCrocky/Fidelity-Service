package com.fidelite.models;

import com.fidelite.enums.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    @NotBlank
    @Column(nullable = false)
    private String name; // Display name of the product

    @Enumerated(EnumType.STRING)
    private ProductType productType; // Category of the product

    @Column(nullable = false)
    private int productPoint; // Points awarded when this product is purchased

    // Whether this product is currently featured in a promotion
    @Column(nullable = false)
    private boolean promotion = false;

    // The merchant that sells this product
    @ManyToOne(optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;
    
}
