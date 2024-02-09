package com.andresantiago.vendorsservice.dto;

import com.andresantiago.vendorsservice.entity.LocationEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorDto {

    private String id;
    private String name;
    private String taxId;
    private boolean isCompliant;
    private LocationEntity location;
    private ServiceCategoryEnum service;

}
