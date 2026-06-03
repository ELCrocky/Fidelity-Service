package com.fidelite.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Fidelite extends Client {
    @Id
    private String idFidelite;

    @Column(nullable = false, length = 13, unique = true)
    private String barcodeFidelite;

    @Column(nullable = false)
    @Min(0)
    private int pointsActuels;

    @OneToOne
    @JoinColumn(name = "product_promotion_name")
    private Product productPromotioné;

    @OneToOne
    @JoinColumn(name = "history_id")
    private History transactionHistory;
    
}
