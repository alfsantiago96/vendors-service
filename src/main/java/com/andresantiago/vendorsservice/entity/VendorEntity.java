package com.andresantiago.vendorsservice.entity;

import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorEntity {

    @Id
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