package com.fidelite.dto.responseDTO;
import com.fidelite.enums.ProductType;

import lombok.Data;

@Data
public class ProductResponseDTO {
    
    private Long id;

    private String name;

    private ProductType productType;

    private int productPoint;

    private boolean promotion;
}