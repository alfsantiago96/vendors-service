package com.andresantiago.vendorsservice.api.v1.request;

import com.andresantiago.vendorsservice.dto.ServiceDto;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateVendorRequest {

    @NotEmpty(message = "Vendor name cannot be empty")
    private String name;

    @NotEmpty(message = "Vendor taxId cannot be empty")
    private String taxId;

    @Valid
    private LocationRequest location;

    @NotEmpty(message = "Vendor services cannot be empty")
    private List<ServiceDto> services;
}