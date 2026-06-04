package com.fidelite.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Redemption {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long redepmtionId;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime reddemedAt = LocalDateTime.now();

    //Relations
    @ManyToOne(optional = false)
    @JoinColumn(name = "card_id", nullable = false)
    private LoyaltyCard card;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reward_id", nullable = false)
    private Reward reward;
    
}
