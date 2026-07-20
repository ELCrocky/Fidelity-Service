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
    date = "2026-07-20T16:25:35+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
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
        clearingUserResponseDTO.setClearingCompanyId( clearingUserClearingCompanyCcId( clearingUser ) );
        clearingUserResponseDTO.setEmail( clearingUser.getEmail() );

        return clearingUserResponseDTO;
    }

    @Override
    public ClearingUser toEntity(ClearingUserRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ClearingUser.ClearingUserBuilder clearingUser = ClearingUser.builder();

        clearingUser.email( dto.getEmail() );

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
