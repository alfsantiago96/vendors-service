package com.andresantiago.vendorsservice.api.v1.request;

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

    @NotEmpty
    private String name;

    @NotEmpty
    private String taxId;

    @Valid
    private LocationRequest location;

    @NotEmpty
    private List<ServiceCategoryEnum> services;
}