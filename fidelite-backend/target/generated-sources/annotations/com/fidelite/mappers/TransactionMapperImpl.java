package com.fidelite.mappers;

import com.fidelite.dto.requests.TransactionRequestDTO;
import com.fidelite.dto.responseDTO.TransactionResponseDTO;
import com.fidelite.models.Transaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-09T15:34:22+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public TransactionResponseDTO toResponse(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();

        transactionResponseDTO.setId( transaction.getTransactionId() );
        transactionResponseDTO.setProductNames( productsToNames( transaction.getProducts() ) );
        transactionResponseDTO.setCreatedAt( transaction.getCreatedAt() );
        transactionResponseDTO.setPoints( transaction.getPoints() );
        transactionResponseDTO.setType( transaction.getType() );

        return transactionResponseDTO;
    }

    @Override
    public Transaction toEntity(TransactionRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Transaction.TransactionBuilder transaction = Transaction.builder();

        transaction.idempotencyKey( dto.getIdempotencyKey() );
        transaction.points( dto.getPoints() );
        transaction.type( dto.getType() );

        return transaction.build();
    }
}
