package com.fidelite.dto.responseDTO;

import java.math.BigDecimal;
import java.util.UUID;


import lombok.Data;

@Data
public class ClearingCompanyResponseDTO {

    private UUID id;
    private String name;
    private BigDecimal totalRevenue;

}
