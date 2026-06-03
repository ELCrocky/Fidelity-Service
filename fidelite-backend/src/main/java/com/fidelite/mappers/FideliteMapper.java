package com.fidelite.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.responseDTO.*;
import com.fidelite.dto.requests.FideliteRequestDTO;
import com.fidelite.models.Fidelite;

@Mapper(componentModel = "spring")
public interface FideliteMapper {

    @Mapping(source = "productPromotioné", target = "productPromotion")
    FideliteResponseDTO toResponse(Fidelite fidelite);

    @Mapping(source = "productPromotioné", target = "productPromotion")
    List<FideliteResponseDTO> toResponse(List<Fidelite> fidelites);

    @Mapping(target = "password", ignore = true)
    @Mapping(source = "productPromotion", target = "productPromotioné")
    @Mapping(target = "productPromotioné.promotion", ignore = true)
    @Mapping(target = "productPromotioné.fidelite", ignore = true)
    @Mapping(target = "transactionHistory.historyId", ignore = true)
    @Mapping(target = "transactionHistory.fidelite", ignore = true)
    @Mapping(target = "transactionHistory.products", ignore = true)
    Fidelite toEntity(FideliteResponseDTO fideliteDTO);

    @Mapping(target = "idFidelite", ignore = true)
    @Mapping(target = "barcodeFidelite", ignore = true)
    @Mapping(target = "pointsActuels", ignore = true)
    @Mapping(target = "productPromotioné", ignore = true)
    @Mapping(target = "transactionHistory", ignore = true)
    Fidelite toEntity(FideliteRequestDTO requestDTO);

}