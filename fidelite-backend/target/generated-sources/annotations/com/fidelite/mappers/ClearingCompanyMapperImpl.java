package com.fidelite.mappers;

import com.fidelite.dto.requests.ClearingCompanyRequestDTO;
import com.fidelite.dto.responseDTO.ClearingCompanyResponseDTO;
import com.fidelite.models.ClearingCompany;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-11T16:48:52+0200",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
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
