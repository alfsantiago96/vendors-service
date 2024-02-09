package com.andresantiago.vendorsservice.validation;

import com.andresantiago.vendorsservice.api.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class VendorValidation {

    public static void validateVendor(VendorEntity vendor, ServiceCategoryEnum serviceCategoryEnum, LocationRequest locationRequest) {
        log.info("Validating vendor...");

        if (Objects.isNull(vendor)) {
            throw new BusinessException("Vendor deost not exists.");
        }

        if (!vendor.isCompliant()) {
            throw new BusinessException("This vendor is not compliant.");
        }

        boolean hasTheService = vendor.getServices().stream().anyMatch(serviceCategoryEnum1 -> serviceCategoryEnum1.equals(serviceCategoryEnum));

        if (!hasTheService) {
            throw new BusinessException("Vendor does not provide the service required.");
        }

        boolean isSameCity = vendor.getLocation().getName().equals(locationRequest.getName());
        boolean isSameState = vendor.getLocation().getState().equals(locationRequest.getState());

        if (!isSameCity || !isSameState) {
            throw new BusinessException("Vendor doest not attend this location.");
        }

        log.info("Vendor validated with success.");
    }
}
