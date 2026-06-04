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
    private String condition;

    @Min(0)
    @Column(nullable = false)
    private int pointsAwarded;

    private LocalDate validForm;

    private LocalDate validTo;

    //Relations
    @ManyToOne(optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

}
