package com.fidelite.models;

import java.util.UUID;

import com.fidelite.enums.MerchantStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Merchant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idMerchant;
    
    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String apiKey;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MerchantStatus status = MerchantStatus.ACTIVE;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();

    //relationlar

    @ManyToOne(optional=false)
    @JoinColumn(name = "pool_id", nullable = false)
    private SettlementPool pool;

    @Column
    private String city;

    @Column
    private String region;
}
