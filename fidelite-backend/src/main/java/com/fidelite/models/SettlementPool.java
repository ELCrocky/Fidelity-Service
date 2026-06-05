package com.fidelite.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.UUID;
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
public class SettlementPool {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID spId;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clearing_company_id", nullable = false)
    private ClearingCompany clearingCompany;

    @Column(nullable = false, precision = 12, scale = 4)
    private BigDecimal pointValue;

    @Column(nullable = false, precision = 14, scale = 2)
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal commissionPerInvoice = BigDecimal.ZERO;
}   
