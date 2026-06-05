package com.fidelite.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fidelite.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false)
    private int points; // Number of points earned or deducted in this transaction

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type; // Whether points were earned (GAIN) or spent (SPENT)

    // Unique key to prevent duplicate transactions from retried requests
    @Column(unique = true)
    private String idempotencyKey;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now(); // Timestamp when the transaction occurred

    // The loyalty card this transaction was made on
    @ManyToOne(optional = false)
    @JoinColumn(name = "card_id", nullable = false)
    private LoyaltyCard card;

    // Products purchased in this transaction (used for point calculation)
    @ManyToMany
    @JoinTable(
        name = "transaction_product",
        joinColumns = @JoinColumn(name = "transaction_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @Builder.Default
    private List<Product> products = new ArrayList<>();
}
