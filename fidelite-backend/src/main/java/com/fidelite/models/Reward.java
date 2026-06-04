package com.fidelite.models;

import java.util.UUID;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reward {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID rewardID;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Min(1)
    @Column(nullable = false)
    private int costPoints;

    //Relations
}
