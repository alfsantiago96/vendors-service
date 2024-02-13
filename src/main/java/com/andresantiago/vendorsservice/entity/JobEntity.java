package com.andresantiago.vendorsservice.entity;

import com.andresantiago.vendorsservice.api.rest.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class JobEntity {

    private String id;
    private String companyTaxId;
    private ServiceCategoryEnum service;
    private LocationRequest location;
    private VendorEntity vendor;
}