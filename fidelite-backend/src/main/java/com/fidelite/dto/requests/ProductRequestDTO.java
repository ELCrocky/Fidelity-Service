package com.fidelite.dto.requests;

import com.fidelite.enums.Ptype;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductRequestDTO {

    private String name;
    private Ptype productType;
    private int productPoint;

}
