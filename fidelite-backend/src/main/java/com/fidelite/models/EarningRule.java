package com.fidelite.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class EarningRule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long earningRuleId;

    @Column(nullable = false)
    private String condition; // Rule expression that determines when points are awarded

    @Min(0)
    @Column(nullable = false)
    private int pointsAwarded; // Number of points given when this rule is triggered

    private LocalDate validFrom; // Date from which this rule is active (null = always active)

    private LocalDate validTo; // Date until which this rule is active (null = no expiry)

    // The merchant this earning rule applies to
    @ManyToOne(optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

}
