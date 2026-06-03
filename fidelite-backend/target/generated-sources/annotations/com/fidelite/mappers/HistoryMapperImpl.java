package com.fidelite.mappers;

import com.fidelite.dto.requests.HistoryRequestDTO;
import com.fidelite.dto.responseDTO.HistoryResponseDTO;
import com.fidelite.enums.Ttype;
import com.fidelite.models.History;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-03T13:46:26+0200",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class HistoryMapperImpl implements HistoryMapper {

    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_HH_mm_ss_02049711904 = DateTimeFormatter.ofPattern( "dd/MM/yyyy HH:mm:ss" );

    @Override
    public HistoryResponseDTO toResponse(History history) {
        if ( history == null ) {
            return null;
        }

        HistoryResponseDTO.HistoryResponseDTOBuilder historyResponseDTO = HistoryResponseDTO.builder();

        if ( history.getTransactionDate() != null ) {
            historyResponseDTO.transactionDate( dateTimeFormatter_dd_MM_yyyy_HH_mm_ss_02049711904.format( history.getTransactionDate() ) );
        }
        historyResponseDTO.points( history.getPoints() );
        historyResponseDTO.transactionId( history.getTransactionId() );
        if ( history.getTransactionType() != null ) {
            historyResponseDTO.transactionType( history.getTransactionType().name() );
        }

        return historyResponseDTO.build();
    }

    @Override
    public List<HistoryResponseDTO> toResponse(List<History> histories) {
        if ( histories == null ) {
            return null;
        }

        List<HistoryResponseDTO> list = new ArrayList<HistoryResponseDTO>( histories.size() );
        for ( History history : histories ) {
            list.add( toResponse( history ) );
        }

        return list;
    }

    @Override
    public History toEntity(HistoryResponseDTO historyDTO) {
        if ( historyDTO == null ) {
            return null;
        }

        History.HistoryBuilder history = History.builder();

        if ( historyDTO.getTransactionDate() != null ) {
            history.transactionDate( LocalDateTime.parse( historyDTO.getTransactionDate(), dateTimeFormatter_dd_MM_yyyy_HH_mm_ss_02049711904 ) );
        }
        history.points( historyDTO.getPoints() );
        history.transactionId( historyDTO.getTransactionId() );
        if ( historyDTO.getTransactionType() != null ) {
            history.transactionType( Enum.valueOf( Ttype.class, historyDTO.getTransactionType() ) );
        }

        return history.build();
    }

    @Override
    public History toEntity(HistoryRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        History.HistoryBuilder history = History.builder();

        history.points( requestDTO.getPoints() );
        if ( requestDTO.getTransactionType() != null ) {
            history.transactionType( Enum.valueOf( Ttype.class, requestDTO.getTransactionType() ) );
        }
        List<String> list = requestDTO.getProducts();
        if ( list != null ) {
            history.products( new ArrayList<String>( list ) );
        }

        return history.build();
    }
}
