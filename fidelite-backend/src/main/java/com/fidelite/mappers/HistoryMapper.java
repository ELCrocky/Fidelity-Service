package com.fidelite.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.responseDTO.HistoryResponseDTO;
import com.fidelite.dto.requests.HistoryRequestDTO;
import com.fidelite.models.History;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    @Mapping(source = "transactionDate", target = "transactionDate", dateFormat = "dd/MM/yyyy HH:mm:ss")
    HistoryResponseDTO toResponse(History history);

    List<HistoryResponseDTO> toResponse(List<History> histories);

    @Mapping(source = "transactionDate", target = "transactionDate", dateFormat = "dd/MM/yyyy HH:mm:ss")
    @Mapping(target = "historyId", ignore = true)
    @Mapping(target = "fidelite", ignore = true)
    @Mapping(target = "products", ignore = true)
    History toEntity(HistoryResponseDTO historyDTO);

    @Mapping(target = "historyId", ignore = true)
    @Mapping(target = "transactionDate", ignore = true)
    @Mapping(target = "fidelite", ignore = true)
    History toEntity(HistoryRequestDTO requestDTO);

}