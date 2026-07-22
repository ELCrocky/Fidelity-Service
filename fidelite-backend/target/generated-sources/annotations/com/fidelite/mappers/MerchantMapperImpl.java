package com.fidelite.mappers;

import com.fidelite.dto.requests.MerchantRequestDTO;
import com.fidelite.dto.responseDTO.MerchantResponseDTO;
import com.fidelite.models.Merchant;
import com.fidelite.models.SettlementPool;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-22T11:53:40+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class MerchantMapperImpl implements MerchantMapper {

    @Override
    public MerchantResponseDTO toResponse(Merchant merchant) {
        if ( merchant == null ) {
            return null;
        }

        MerchantResponseDTO merchantResponseDTO = new MerchantResponseDTO();

        merchantResponseDTO.setId( merchant.getIdMerchant() );
        merchantResponseDTO.setPoolId( merchantPoolSpId( merchant ) );
        merchantResponseDTO.setName( merchant.getName() );
        merchantResponseDTO.setRegion( merchant.getRegion() );
        merchantResponseDTO.setStatus( merchant.getStatus() );

        return merchantResponseDTO;
    }

    @Override
    public Merchant toEntity(MerchantRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Merchant.MerchantBuilder merchant = Merchant.builder();

        merchant.city( dto.getCity() );
        merchant.name( dto.getName() );
        merchant.region( dto.getRegion() );

        return merchant.build();
    }

    private UUID merchantPoolSpId(Merchant merchant) {
        SettlementPool pool = merchant.getPool();
        if ( pool == null ) {
            return null;
        }
        return pool.getSpId();
    }
}
