package com.fidelite.mappers;

import com.fidelite.dto.requests.TransactionRequestDTO;
import com.fidelite.dto.responseDTO.TransactionResponseDTO;
import com.fidelite.models.Transaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T16:11:34+0200",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
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
        transactionResponseDTO.setPoints( transaction.getPoints() );
        transactionResponseDTO.setType( transaction.getType() );
        transactionResponseDTO.setCreatedAt( transaction.getCreatedAt() );

        return transactionResponseDTO;
    }

    @Override
    public Transaction toEntity(TransactionRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Transaction.TransactionBuilder transaction = Transaction.builder();

        transaction.points( dto.getPoints() );
        transaction.type( dto.getType() );
        transaction.idempotencyKey( dto.getIdempotencyKey() );

        return transaction.build();
    }
}
