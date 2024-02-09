package com.andresantiago.vendorsservice.dto;

import com.andresantiago.vendorsservice.api.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {

    public String id;
    public String companyTaxId;
    public String vendorName;
    public String vendorTaxId;
    public ServiceCategoryEnum serviceCategory;
    public LocationRequest locationRequest;
    public LocalDateTime hireDate;
}