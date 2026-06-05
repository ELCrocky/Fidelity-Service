package com.fidelite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.requests.ProductRequestDTO;
import com.fidelite.dto.responseDTO.ProductResponseDTO;
import com.fidelite.models.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "idProduct", target = "id")
    ProductResponseDTO toResponse(Product product);

    @Mapping(target = "idProduct", ignore = true)
    @Mapping(target = "merchant", ignore = true) // Merchant is resolved in service
    Product toEntity(ProductRequestDTO dto);
}
