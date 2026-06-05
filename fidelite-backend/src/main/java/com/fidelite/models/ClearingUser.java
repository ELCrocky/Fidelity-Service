package com.fidelite.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class ClearingUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cuId;

    // The clearing company this user belongs to
    @ManyToOne(optional = false)
    @JoinColumn(name = "clearing_company_id", nullable = false)
    private ClearingCompany clearingCompany;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String passwordHash; // Hashed password for authentication
}
