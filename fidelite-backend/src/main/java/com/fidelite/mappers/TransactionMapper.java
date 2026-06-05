package com.fidelite.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.fidelite.dto.requests.TransactionRequestDTO;
import com.fidelite.dto.responseDTO.TransactionResponseDTO;
import com.fidelite.models.Product;
import com.fidelite.models.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "transactionId", target = "id")
    @Mapping(source = "products", target = "productNames", qualifiedByName = "productsToNames")
    TransactionResponseDTO toResponse(Transaction transaction);

    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "card", ignore = true)      // Card is resolved in service
    @Mapping(target = "products", ignore = true)  // Products are resolved in service
    @Mapping(target = "createdAt", ignore = true)
    Transaction toEntity(TransactionRequestDTO dto);

    @Named("productsToNames")
    default List<String> productsToNames(List<Product> products) {
        if (products == null) return List.of();
        return products.stream().map(Product::getName).toList();
    }
}
