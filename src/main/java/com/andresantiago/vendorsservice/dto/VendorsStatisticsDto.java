package com.andresantiago.vendorsservice.dto;

import com.andresantiago.vendorsservice.api.rest.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import lombok.*;


@Builder
@AllArgsConstructor
@Getter
public class VendorsStatisticsDto {

    public ServiceCategoryEnum serviceCategory;
    public LocationRequest location;
    public int totalVendors;
    public int totalCompliant;
    public int totalNotCompliant;
}