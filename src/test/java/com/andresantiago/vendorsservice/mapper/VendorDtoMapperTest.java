package com.andresantiago.vendorsservice.mapper;

import com.andresantiago.vendorsservice.dto.VendorDto;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.stubs.VendorStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VendorDtoMapperTest {

    @Test
    void shouldMapEntityToDtoWithSuccess()  {
        final VendorEntity vendorEntity = VendorStub.createEntity();

        final VendorDto vendorDto = VendorDtoMapper.map(vendorEntity, true, ServiceCategoryEnum.AIR_CONDITIONING);

        assertAll(
                () -> assertEquals("Vendor 1", vendorDto.getName()),
                () -> assertEquals("1", vendorDto.getTaxId()),
                () -> assertTrue(vendorDto.isCompliant()),
                () -> assertEquals(ServiceCategoryEnum.AIR_CONDITIONING, vendorDto.getService()),
                () -> assertEquals("Capivari do Sul", vendorDto.getLocation().getName()),
                () -> assertEquals("RS", vendorDto.getLocation().getState())
        );
    }
}
