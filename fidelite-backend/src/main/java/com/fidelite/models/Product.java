package com.fidelite.models;

import com.fidelite.enums.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
public class Product {

    @Id
    private String name;

    @Enumerated(EnumType.STRING)
    private Ptype productType;

    @Column(nullable = false)
    private int productPoint;

    @Builder.Default
    @Column(nullable = false)
    private boolean promotion = false;

    @OneToOne(mappedBy = "productPromotioné")
    private Fidelite fidelite;

}
