package com.fidelite.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

public class ClearingCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ccId;

    @NotBlank
    @Column(nullable = false)
    private String name; // Legal name of the clearing company

    // Cumulative revenue across all settlement pools
    @Column(nullable = false, precision = 16, scale=2)
    @Builder.Default
    private BigDecimal totalRevenue = BigDecimal.ZERO;

    // Staff/operators who belong to this clearing company
    @OneToMany(mappedBy = "clearingCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ClearingUser> users = new ArrayList<>();

    // Settlement pools managed by this clearing company
    @OneToMany(mappedBy = "clearingCompany")
    @Builder.Default
    private List<SettlementPool> pools = new ArrayList<>();


}
