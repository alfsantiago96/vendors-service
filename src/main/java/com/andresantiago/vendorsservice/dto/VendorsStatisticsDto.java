package com.andresantiago.vendorsservice.dto;

import com.andresantiago.vendorsservice.api.request.LocationRequest;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorsStatisticsDto {

    public ServiceCategoryEnum serviceCategory;
    public LocationRequest location;
    public int totalVendors;
    public int totalCompliant;
    public int totalNotCompliant;
}