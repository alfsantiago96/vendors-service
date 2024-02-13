package com.andresantiago.vendorsservice.entity;

import com.andresantiago.vendorsservice.dto.ServiceDto;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
public class VendorEntity {

    private String name;
    private String taxId;
    private LocationEntity location;
    private List<ServiceDto> services;

    public void addService(ServiceCategoryEnum newService, boolean isCompliant) {
        if (Objects.isNull(services)) {
            services = new ArrayList<>();
        }
        ServiceDto serviceDto = ServiceDto.builder()
                .serviceCategory(newService)
                .isCompliant(isCompliant)
                .build();
        services.add(serviceDto);
    }
}