package com.fidelite.mappers;

import com.fidelite.dto.requests.ProductRequestDTO;
import com.fidelite.dto.responseDTO.ProductResponseDTO;
import com.fidelite.models.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T16:10:41+0200",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
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
        productResponseDTO.setProductType( product.getProductType() );
        productResponseDTO.setProductPoint( product.getProductPoint() );
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
        product.setProductType( dto.getProductType() );
        product.setProductPoint( dto.getProductPoint() );
        product.setPromotion( dto.isPromotion() );

        return product;
    }
}
