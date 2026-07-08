package com.fidelite.mappers;

import com.fidelite.dto.requests.ClearingCompanyRequestDTO;
import com.fidelite.dto.responseDTO.ClearingCompanyResponseDTO;
import com.fidelite.models.ClearingCompany;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-08T16:08:31+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class ClearingCompanyMapperImpl implements ClearingCompanyMapper {

    @Override
    public ClearingCompanyResponseDTO toResponse(ClearingCompany clearingCompany) {
        if ( clearingCompany == null ) {
            return null;
        }

        ClearingCompanyResponseDTO clearingCompanyResponseDTO = new ClearingCompanyResponseDTO();

        clearingCompanyResponseDTO.setId( clearingCompany.getCcId() );
        clearingCompanyResponseDTO.setName( clearingCompany.getName() );
        clearingCompanyResponseDTO.setTotalRevenue( clearingCompany.getTotalRevenue() );

        return clearingCompanyResponseDTO;
    }

    @Override
    public ClearingCompany toEntity(ClearingCompanyRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ClearingCompany.ClearingCompanyBuilder clearingCompany = ClearingCompany.builder();

        clearingCompany.name( dto.getName() );

        return clearingCompany.build();
    }
}
