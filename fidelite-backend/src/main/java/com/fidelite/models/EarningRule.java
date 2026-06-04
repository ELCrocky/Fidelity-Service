package com.fidelite.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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
}
