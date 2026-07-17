package com.fidelite.mappers;

import com.fidelite.dto.requests.CustomerRequestDTO;
import com.fidelite.dto.responseDTO.CustomerResponseDTO;
import com.fidelite.models.Customer;
import com.fidelite.models.Tier;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T15:30:15+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
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
        customerResponseDTO.setEmail( customer.getEmail() );
        customerResponseDTO.setNom( customer.getNom() );
        customerResponseDTO.setPhone( customer.getPhone() );
        customerResponseDTO.setPrenom( customer.getPrenom() );

        return customerResponseDTO;
    }

    @Override
    public Customer toEntity(CustomerRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.dateDeNaissance( dto.getDateNaissance() );
        customer.email( dto.getEmail() );
        customer.marketingConsent( dto.isMarketingConsent() );
        customer.nom( dto.getNom() );
        customer.phone( dto.getPhone() );
        customer.prenom( dto.getPrenom() );

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
