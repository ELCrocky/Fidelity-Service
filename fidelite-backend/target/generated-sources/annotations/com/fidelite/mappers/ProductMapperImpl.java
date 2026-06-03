package com.fidelite.mappers;

import com.fidelite.dto.requests.ProductRequestDTO;
import com.fidelite.dto.responseDTO.ProductResponseDTO;
import com.fidelite.models.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-03T13:44:49+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponseDTO toResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponseDTO.ProductResponseDTOBuilder productResponseDTO = ProductResponseDTO.builder();

        productResponseDTO.name( product.getName() );
        productResponseDTO.productType( product.getProductType() );
        productResponseDTO.productPoint( product.getProductPoint() );

        return productResponseDTO.build();
    }

    @Override
    public List<ProductResponseDTO> toResponse(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductResponseDTO> list = new ArrayList<ProductResponseDTO>( products.size() );
        for ( Product product : products ) {
            list.add( toResponse( product ) );
        }

        return list;
    }

    @Override
    public Product toEntity(ProductResponseDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( productDTO.getName() );
        product.productType( productDTO.getProductType() );
        product.productPoint( productDTO.getProductPoint() );

        return product.build();
    }

    @Override
    public Product toEntity(ProductRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( requestDTO.getName() );
        product.productType( requestDTO.getProductType() );
        product.productPoint( requestDTO.getProductPoint() );

        return product.build();
    }
}
