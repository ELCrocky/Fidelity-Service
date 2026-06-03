package com.fidelite.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.requests.ProductRequestDTO;
import com.fidelite.dto.responseDTO.ProductResponseDTO;
import com.fidelite.models.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDTO toResponse(Product product);

    List<ProductResponseDTO> toResponse(List<Product> products);

    @Mapping(target = "promotion", ignore = true)
    @Mapping(target = "fidelite", ignore = true)
    Product toEntity(ProductResponseDTO productDTO);

    @Mapping(target = "promotion", ignore = true)
    @Mapping(target = "fidelite", ignore = true)
    Product toEntity(ProductRequestDTO requestDTO);

}
