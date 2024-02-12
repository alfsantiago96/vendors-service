package com.andresantiago.vendorsservice.dto;

import com.andresantiago.vendorsservice.api.rest.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class JobDto {

    public String id;
    public String companyTaxId;
    public String vendorName;
    public String vendorTaxId;
    public ServiceCategoryEnum serviceCategory;
    public LocationRequest locationRequest;
    @JsonFormat(pattern="yyyy-MM-dd")
    public LocalDateTime hireDate;
}