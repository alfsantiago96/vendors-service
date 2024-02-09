package com.andresantiago.vendorsservice.validation;

import com.andresantiago.vendorsservice.api.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.dto.ServiceDto;
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

        if (!isSameLocation(vendor, locationRequest)) {
            throw new BusinessException("Vendor doest not attend this location.");
        }

        if (!isOfferingTheService(vendor, serviceCategoryEnum)) {
            throw new BusinessException("Vendor does not provide the service required.");
        }
        log.info("Vendor validated with success.");
    }

    public static boolean isOfferingTheService(VendorEntity vendor, ServiceCategoryEnum serviceCategoryEnum) {
        return vendor.getServices().stream()
                .anyMatch(serviceDto -> serviceDto.getServiceCategory().equals(serviceCategoryEnum));
    }

    public static boolean isCompliantByService(ServiceCategoryEnum serviceCategoryEnum, VendorEntity vendor) {
        return vendor.getServices().stream()
                .filter(serviceDto -> serviceDto.getServiceCategory().equals(serviceCategoryEnum))
                .anyMatch(ServiceDto::isCompliant);
    }

    public static boolean isSameLocation(VendorEntity vendor, LocationRequest locationRequest) {
        boolean isSameCity = vendor.getLocation().getName().equals(locationRequest.getName());
        boolean isSameState = vendor.getLocation().getState().equals(locationRequest.getState());

        return isSameCity && isSameState;
    }
}