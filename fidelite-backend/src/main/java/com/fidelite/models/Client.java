package com.fidelite.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String mailClient;

    @Column(nullable = false)
    private String password;
}
