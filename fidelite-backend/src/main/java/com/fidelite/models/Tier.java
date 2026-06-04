package com.fidelite.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
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
public class Tier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tierID;
    
    @NotBlank
    @Column(nullable = false)
    private String name;

    @Min(0)
    @Column(nullable = false)
    private int minPoints;

    @Builder.Default
    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal multiplier = new BigDecimal("1.0");
    
    //relations
    @ManyToOne(optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;
    
}
