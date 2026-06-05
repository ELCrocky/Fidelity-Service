package com.fidelite.mappers;

import com.fidelite.dto.requests.ClearingUserRequestDTO;
import com.fidelite.dto.responseDTO.ClearingUserResponseDTO;
import com.fidelite.models.ClearingCompany;
import com.fidelite.models.ClearingUser;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T16:10:48+0200",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class ClearingUserMapperImpl implements ClearingUserMapper {

    @Override
    public ClearingUserResponseDTO toResponse(ClearingUser clearingUser) {
        if ( clearingUser == null ) {
            return null;
        }

        ClearingUserResponseDTO clearingUserResponseDTO = new ClearingUserResponseDTO();

        clearingUserResponseDTO.setId( clearingUser.getCuId() );
        clearingUserResponseDTO.setEmail( clearingUser.getPasswordHash() );
        clearingUserResponseDTO.setClearingCompanyId( clearingUserClearingCompanyCcId( clearingUser ) );

        return clearingUserResponseDTO;
    }

    @Override
    public ClearingUser toEntity(ClearingUserRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ClearingUser.ClearingUserBuilder clearingUser = ClearingUser.builder();

        return clearingUser.build();
    }

    private UUID clearingUserClearingCompanyCcId(ClearingUser clearingUser) {
        ClearingCompany clearingCompany = clearingUser.getClearingCompany();
        if ( clearingCompany == null ) {
            return null;
        }
        return clearingCompany.getCcId();
    }
}
