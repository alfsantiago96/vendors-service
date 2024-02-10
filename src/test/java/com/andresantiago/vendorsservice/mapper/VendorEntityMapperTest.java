package com.andresantiago.vendorsservice.mapper;

import com.andresantiago.vendorsservice.api.v1.request.CreateVendorRequest;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.stubs.CreateVendorRequestStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VendorEntityMapperTest {

    @Test
    void shouldMapRequestToEntityWithSuccess() {
        final CreateVendorRequest aValidRequest = CreateVendorRequestStub.createAValidRequest();

        final VendorEntity vendorEntity = VendorEntityMapper.map(aValidRequest);

        assertAll(

                () -> assertEquals("New Vendor", vendorEntity.getName()),
                () -> assertEquals("1", vendorEntity.getTaxId()),
                () -> assertEquals("Capivari do Sul", vendorEntity.getLocation().getName()),
                () -> assertEquals("RS", vendorEntity.getLocation().getState()),
                () -> assertNotNull(vendorEntity.getServices()),
                () -> assertNotNull(vendorEntity.getServices().get(0))
        );
    }
}
