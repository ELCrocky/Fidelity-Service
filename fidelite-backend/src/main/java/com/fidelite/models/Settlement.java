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

    @ManyToOne(optional = false)
    @JoinColumn(name = "pool_id", nullable = false)
    private SettlementPool pool;

    @Column(nullable = false)
    private LocalDate periodStart;


    @Column(nullable = false)
    private LocalDate periodEnd;

    @Column(nullable = false)
    private long pointsIssued;

    @Column(nullable = false)
    private long pointsRedeemed;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal netAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal commision = BigDecimal.ZERO;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "settlement", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Settlement> lines = new ArrayList<>();


}
