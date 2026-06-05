package com.fidelite.mappers;

import com.fidelite.dto.requests.CustomerRequestDTO;
import com.fidelite.dto.responseDTO.CustomerResponseDTO;
import com.fidelite.models.Customer;
import com.fidelite.models.Tier;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T16:11:26+0200",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerResponseDTO toResponse(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();

        customerResponseDTO.setId( customer.getCustomerId() );
        customerResponseDTO.setDateNaissance( customer.getDateDeNaissance() );
        customerResponseDTO.setMarkettingConsent( customer.isMarketingConsent() );
        customerResponseDTO.setTierName( customerTierName( customer ) );
        customerResponseDTO.setNom( customer.getNom() );
        customerResponseDTO.setPrenom( customer.getPrenom() );
        customerResponseDTO.setEmail( customer.getEmail() );
        customerResponseDTO.setPhone( customer.getPhone() );

        return customerResponseDTO;
    }

    @Override
    public Customer toEntity(CustomerRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.dateDeNaissance( dto.getDateNaissance() );
        customer.nom( dto.getNom() );
        customer.prenom( dto.getPrenom() );
        customer.email( dto.getEmail() );
        customer.phone( dto.getPhone() );
        customer.marketingConsent( dto.isMarketingConsent() );

        return customer.build();
    }

    private String customerTierName(Customer customer) {
        Tier tier = customer.getTier();
        if ( tier == null ) {
            return null;
        }
        return tier.getName();
    }
}
