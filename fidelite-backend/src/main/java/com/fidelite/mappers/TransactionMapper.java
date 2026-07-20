package com.fidelite.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.fidelite.dto.requests.TransactionRequestDTO;
import com.fidelite.dto.responseDTO.TransactionResponseDTO;
import com.fidelite.models.Customer;
import com.fidelite.models.Product;
import com.fidelite.models.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "transactionId", target = "id")
    @Mapping(source = "products", target = "productNames", qualifiedByName = "productsToNames")
    @Mapping(source = "card.customer", target = "customerName", qualifiedByName = "customerToName")
    @Mapping(source = "card.barcodeEan13", target = "cardBarcode")
    TransactionResponseDTO toResponse(Transaction transaction);

    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "card", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Transaction toEntity(TransactionRequestDTO dto);

    @Named("productsToNames")
    default List<String> productsToNames(List<Product> products) {
        if (products == null) return List.of();
        return products.stream().map(Product::getName).toList();
    }

    @Named("customerToName")
    default String customerToName(Customer customer) {
        if (customer == null) return "";
        return customer.getPrenom() + " " + customer.getNom();
    }
}
