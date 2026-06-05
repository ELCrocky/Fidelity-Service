package com.fidelite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.requests.CustomerRequestDTO;
import com.fidelite.dto.responseDTO.CustomerResponseDTO;
import com.fidelite.models.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(source = "customerId", target = "id")
    @Mapping(source = "dateDeNaissance", target = "dateNaissance")
    @Mapping(source = "marketingConsent", target = "markettingConsent")
    @Mapping(source = "tier.name", target = "tierName")
    CustomerResponseDTO toResponse(Customer customer);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "merchant", ignore = true) // Merchant is resolved in service
    @Mapping(target = "tier", ignore = true)     // Tier is assigned separately
    @Mapping(target = "cards", ignore = true)
    @Mapping(source = "dateNaissance", target = "dateDeNaissance")
    Customer toEntity(CustomerRequestDTO dto);
}
