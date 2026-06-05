package com.fidelite.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
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
public class SettlementLine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The parent settlement this line belongs to
    @ManyToOne(optional = false)
    @JoinColumn(name = "settlement_id", nullable = false)
    private Settlement settlement;

    // The merchant this line represents
    @ManyToOne(optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(nullable = false)
    private long pointsIssued; // Points issued by this merchant during the period

    @Column(nullable=false)
    private long pointsRedeemed; // Points redeemed at this merchant during the period

    @Column(nullable = false)
    private long netPoints; // Net points balance (issued - redeemed)

    // Monetary amount owed to/from this merchant based on net points
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal netAmount;
}
