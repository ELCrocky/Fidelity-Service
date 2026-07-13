package com.fidelite.mappers;

import com.fidelite.dto.requests.AppUserRequestDTO;
import com.fidelite.dto.responseDTO.AppUserResponseDTO;
import com.fidelite.models.AppUser;
import com.fidelite.models.Merchant;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-09T15:34:22+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class AppUserMapperImpl implements AppUserMapper {

    @Override
    public AppUserResponseDTO toResponse(AppUser appUser) {
        if ( appUser == null ) {
            return null;
        }

        AppUserResponseDTO appUserResponseDTO = new AppUserResponseDTO();

        appUserResponseDTO.setId( appUser.getAppUserId() );
        appUserResponseDTO.setMerchantId( appUserMerchantIdMerchant( appUser ) );
        appUserResponseDTO.setEmail( appUser.getEmail() );
        appUserResponseDTO.setRole( appUser.getRole() );

        return appUserResponseDTO;
    }

    @Override
    public AppUser toEntity(AppUserRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        AppUser.AppUserBuilder appUser = AppUser.builder();

        appUser.email( dto.getEmail() );
        appUser.role( dto.getRole() );

        return appUser.build();
    }

    private UUID appUserMerchantIdMerchant(AppUser appUser) {
        Merchant merchant = appUser.getMerchant();
        if ( merchant == null ) {
            return null;
        }
        return merchant.getIdMerchant();
    }
}
