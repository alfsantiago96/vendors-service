package com.andresantiago.vendorsservice.validation;

import com.andresantiago.vendorsservice.api.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.exception.BusinessException;
import com.andresantiago.vendorsservice.stubs.LocationStub;
import com.andresantiago.vendorsservice.stubs.VendorStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VendorValidationTest {


    @Test
    void shouldThrowBusinessException_whenVendorDoesNotExists() {
        VendorEntity vendorEntity = null;
        ServiceCategoryEnum serviceCategory = ServiceCategoryEnum.AIR_CONDITIONING;
        LocationRequest location = LocationStub.createRequest("Capivari do Sul", "RS");

        BusinessException businessException = assertThrows(BusinessException.class,
                () -> VendorValidation.validateVendor(vendorEntity, serviceCategory, location));
        assertEquals("Vendor deost not exists.", businessException.getMessage());
    }

    @Test
    void shouldThrowBusinessException_whenVendorDoesNotAttendTheLocation() {
        VendorEntity vendorEntity = VendorStub.createEntity();
        ServiceCategoryEnum serviceCategory = ServiceCategoryEnum.AIR_CONDITIONING;
        LocationRequest location = LocationStub.createRequest("Osório", "RS");

        BusinessException businessException = assertThrows(BusinessException.class,
                () -> VendorValidation.validateVendor(vendorEntity, serviceCategory, location));

        assertEquals("Vendor doest not attend this location.", businessException.getMessage());
    }

    @Test
    void shouldThrowBusinessException_whenVendorDoesNotProvideTheService() {
        VendorEntity vendorEntity = VendorStub.createEntity();
        ServiceCategoryEnum serviceCategory = ServiceCategoryEnum.LANDSCAPING_MAINTENANCE;
        LocationRequest location = LocationStub.createRequest("Capivari do Sul", "RS");

        BusinessException businessException = assertThrows(BusinessException.class,
                () -> VendorValidation.validateVendor(vendorEntity, serviceCategory, location));

        assertEquals("Vendor does not provide the service required.", businessException.getMessage());
    }

    @Test
    void shouldDoNothing_whenVendorIsEligibleForTheJob() {
        VendorEntity vendorEntity = VendorStub.createEntity();
        ServiceCategoryEnum serviceCategory = ServiceCategoryEnum.AIR_CONDITIONING;
        LocationRequest location = LocationStub.createRequest("Capivari do Sul", "RS");

        VendorValidation.validateVendor(vendorEntity, serviceCategory, location);
    }

    @Test
    void shouldReturnFalse_whenVendorIsNotCompliantForTheService() {
        VendorEntity vendorEntity = VendorStub.createEntityNotCompliant();
        ServiceCategoryEnum serviceCategory = ServiceCategoryEnum.AIR_CONDITIONING;

        boolean isCompliantByService = VendorValidation.isCompliantByService(serviceCategory, vendorEntity);

        assertFalse(isCompliantByService);
    }


}
