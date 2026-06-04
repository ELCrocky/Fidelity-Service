package com.fidelite.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal multiplier = new BigDecimal("1.0");
    
    //relations
}
