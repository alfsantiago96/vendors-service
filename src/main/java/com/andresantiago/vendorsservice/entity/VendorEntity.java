package com.andresantiago.vendorsservice.entity;

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
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorEntity {

    private String id;
    private String name;
    private String taxId;
    private boolean isCompliant;
    private LocationEntity location;
    private List<ServiceCategoryEnum> services;

    public void addService(ServiceCategoryEnum newService) {
        if (Objects.isNull(services)) {
            services = new ArrayList<>();
        }
        services.add(newService);
    }
}