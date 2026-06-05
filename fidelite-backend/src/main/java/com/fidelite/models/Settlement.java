package com.fidelite.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
public class Settlement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long settlementId;

    // The pool this settlement belongs to
    @ManyToOne(optional = false)
    @JoinColumn(name = "pool_id", nullable = false)
    private SettlementPool pool;

    @Column(nullable = false)
    private LocalDate periodStart; // Start date of the billing period

    @Column(nullable = false)
    private LocalDate periodEnd; // End date of the billing period

    @Column(nullable = false)
    private long pointsIssued; // Total points issued to customers during this period

    @Column(nullable = false)
    private long pointsRedeemed; // Total points redeemed by customers during this period

    // Net monetary amount owed after points are balanced
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal netAmount;

    // Clearing company commission for this settlement
    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal commision = BigDecimal.ZERO;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(); // Timestamp when settlement was created

    // Per-merchant breakdown lines for this settlement
    @OneToMany(mappedBy = "settlement", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Settlement> lines = new ArrayList<>();


}
