package com.fidelite.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Redemption {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long redepmtionId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime reddemedAt = LocalDateTime.now();

    //Relations
    
}
