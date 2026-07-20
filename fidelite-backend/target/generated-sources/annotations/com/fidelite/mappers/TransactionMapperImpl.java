package com.fidelite.mappers;

import com.fidelite.dto.requests.TransactionRequestDTO;
import com.fidelite.dto.responseDTO.TransactionResponseDTO;
import com.fidelite.models.Customer;
import com.fidelite.models.LoyaltyCard;
import com.fidelite.models.Transaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-20T16:29:37+0300",
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
        transactionResponseDTO.setCustomerName( customerToName( transactionCardCustomer( transaction ) ) );
        transactionResponseDTO.setCardBarcode( transactionCardBarcodeEan13( transaction ) );
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

    private Customer transactionCardCustomer(Transaction transaction) {
        LoyaltyCard card = transaction.getCard();
        if ( card == null ) {
            return null;
        }
        return card.getCustomer();
    }

    private String transactionCardBarcodeEan13(Transaction transaction) {
        LoyaltyCard card = transaction.getCard();
        if ( card == null ) {
            return null;
        }
        return card.getBarcodeEan13();
    }
}
