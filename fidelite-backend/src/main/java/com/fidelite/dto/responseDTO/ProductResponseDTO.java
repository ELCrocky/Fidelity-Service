package com.fidelite.dto.responseDTO;
import com.fidelite.enums.ProductType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponseDTO {
    private String name;

    private ProductType productType;

    private int productPoint;
}