package com.fidelite.dto.responseDTO;
import com.fidelite.enums.Ptype;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponseDTO {
    private String name;

    private Ptype productType;

    private int productPoint;
}