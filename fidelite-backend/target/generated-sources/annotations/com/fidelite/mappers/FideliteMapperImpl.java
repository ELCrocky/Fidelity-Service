package com.fidelite.mappers;

import com.fidelite.dto.requests.FideliteRequestDTO;
import com.fidelite.dto.responseDTO.FideliteResponseDTO;
import com.fidelite.dto.responseDTO.HistoryResponseDTO;
import com.fidelite.dto.responseDTO.ProductResponseDTO;
import com.fidelite.enums.Ttype;
import com.fidelite.models.Fidelite;
import com.fidelite.models.History;
import com.fidelite.models.Product;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-03T13:46:27+0200",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class FideliteMapperImpl implements FideliteMapper {

    @Override
    public FideliteResponseDTO toResponse(Fidelite fidelite) {
        if ( fidelite == null ) {
            return null;
        }

        FideliteResponseDTO.FideliteResponseDTOBuilder fideliteResponseDTO = FideliteResponseDTO.builder();

        fideliteResponseDTO.productPromotion( productToProductResponseDTO( fidelite.getProductPromotioné() ) );
        fideliteResponseDTO.idFidelite( fidelite.getIdFidelite() );
        fideliteResponseDTO.barcodeFidelite( fidelite.getBarcodeFidelite() );
        fideliteResponseDTO.pointsActuels( fidelite.getPointsActuels() );
        fideliteResponseDTO.nom( fidelite.getNom() );
        fideliteResponseDTO.prenom( fidelite.getPrenom() );
        fideliteResponseDTO.mailClient( fidelite.getMailClient() );
        fideliteResponseDTO.transactionHistory( historyToHistoryResponseDTO( fidelite.getTransactionHistory() ) );

        return fideliteResponseDTO.build();
    }

    @Override
    public List<FideliteResponseDTO> toResponse(List<Fidelite> fidelites) {
        if ( fidelites == null ) {
            return null;
        }

        List<FideliteResponseDTO> list = new ArrayList<FideliteResponseDTO>( fidelites.size() );
        for ( Fidelite fidelite : fidelites ) {
            list.add( toResponse( fidelite ) );
        }

        return list;
    }

    @Override
    public Fidelite toEntity(FideliteResponseDTO fideliteDTO) {
        if ( fideliteDTO == null ) {
            return null;
        }

        Fidelite.FideliteBuilder<?, ?> fidelite = Fidelite.builder();

        fidelite.productPromotioné( productResponseDTOToProduct( fideliteDTO.getProductPromotion() ) );
        fidelite.nom( fideliteDTO.getNom() );
        fidelite.prenom( fideliteDTO.getPrenom() );
        fidelite.mailClient( fideliteDTO.getMailClient() );
        fidelite.idFidelite( fideliteDTO.getIdFidelite() );
        fidelite.barcodeFidelite( fideliteDTO.getBarcodeFidelite() );
        fidelite.pointsActuels( fideliteDTO.getPointsActuels() );
        fidelite.transactionHistory( historyResponseDTOToHistory( fideliteDTO.getTransactionHistory() ) );

        return fidelite.build();
    }

    @Override
    public Fidelite toEntity(FideliteRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Fidelite.FideliteBuilder<?, ?> fidelite = Fidelite.builder();

        fidelite.nom( requestDTO.getNom() );
        fidelite.prenom( requestDTO.getPrenom() );
        fidelite.mailClient( requestDTO.getMailClient() );
        fidelite.password( requestDTO.getPassword() );

        return fidelite.build();
    }

    protected ProductResponseDTO productToProductResponseDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponseDTO.ProductResponseDTOBuilder productResponseDTO = ProductResponseDTO.builder();

        productResponseDTO.name( product.getName() );
        productResponseDTO.productType( product.getProductType() );
        productResponseDTO.productPoint( product.getProductPoint() );

        return productResponseDTO.build();
    }

    protected HistoryResponseDTO historyToHistoryResponseDTO(History history) {
        if ( history == null ) {
            return null;
        }

        HistoryResponseDTO.HistoryResponseDTOBuilder historyResponseDTO = HistoryResponseDTO.builder();

        historyResponseDTO.points( history.getPoints() );
        historyResponseDTO.transactionId( history.getTransactionId() );
        if ( history.getTransactionDate() != null ) {
            historyResponseDTO.transactionDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( history.getTransactionDate() ) );
        }
        if ( history.getTransactionType() != null ) {
            historyResponseDTO.transactionType( history.getTransactionType().name() );
        }

        return historyResponseDTO.build();
    }

    protected Product productResponseDTOToProduct(ProductResponseDTO productResponseDTO) {
        if ( productResponseDTO == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( productResponseDTO.getName() );
        product.productPoint( productResponseDTO.getProductPoint() );
        product.productType( productResponseDTO.getProductType() );

        return product.build();
    }

    protected History historyResponseDTOToHistory(HistoryResponseDTO historyResponseDTO) {
        if ( historyResponseDTO == null ) {
            return null;
        }

        History.HistoryBuilder history = History.builder();

        history.points( historyResponseDTO.getPoints() );
        if ( historyResponseDTO.getTransactionDate() != null ) {
            history.transactionDate( LocalDateTime.parse( historyResponseDTO.getTransactionDate() ) );
        }
        history.transactionId( historyResponseDTO.getTransactionId() );
        if ( historyResponseDTO.getTransactionType() != null ) {
            history.transactionType( Enum.valueOf( Ttype.class, historyResponseDTO.getTransactionType() ) );
        }

        return history.build();
    }
}
