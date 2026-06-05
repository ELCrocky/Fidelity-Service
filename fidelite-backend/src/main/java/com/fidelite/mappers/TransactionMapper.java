package com.fidelite.mappers;

import org.mapstruct.Mapper;

import com.fidelite.dto.requests.TransactionRequestDTO;
import com.fidelite.dto.responseDTO.TransactionResponseDTO;
import com.fidelite.models.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

}
