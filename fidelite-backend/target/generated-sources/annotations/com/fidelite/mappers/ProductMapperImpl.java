package com.fidelite.mappers;

import com.fidelite.dto.requests.ProductRequestDTO;
import com.fidelite.dto.responseDTO.ProductResponseDTO;
import com.fidelite.models.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-22T11:53:39+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponseDTO toResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();

        productResponseDTO.setId( product.getIdProduct() );
        productResponseDTO.setName( product.getName() );
        productResponseDTO.setProductPoint( product.getProductPoint() );
        productResponseDTO.setProductType( product.getProductType() );
        productResponseDTO.setPromotion( product.isPromotion() );

        return productResponseDTO;
    }

    @Override
    public Product toEntity(ProductRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Product product = new Product();

        product.setName( dto.getName() );
        product.setProductPoint( dto.getProductPoint() );
        product.setProductType( dto.getProductType() );
        product.setPromotion( dto.isPromotion() );

        return product;
    }
}
