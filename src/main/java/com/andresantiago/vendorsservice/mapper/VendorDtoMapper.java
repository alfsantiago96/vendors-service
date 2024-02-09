package com.andresantiago.vendorsservice.mapper;

import com.andresantiago.vendorsservice.dto.VendorDto;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;

public class VendorDtoMapper {

    public static VendorDto map(VendorEntity vendorEntity, boolean isCompliant, ServiceCategoryEnum serviceCategory) {
        return VendorDto.builder()
                .name(vendorEntity.getName())
                .taxId(vendorEntity.getTaxId())
                .isCompliant(isCompliant)
                .service(serviceCategory)
                .location(vendorEntity.getLocation())
                .build();
    }
}
