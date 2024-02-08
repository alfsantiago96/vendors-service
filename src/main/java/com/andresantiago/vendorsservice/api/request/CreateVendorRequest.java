package com.andresantiago.vendorsservice.api.request;

import com.andresantiago.vendorsservice.entity.LocationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateVendorRequest {

    private String name;
    private String taxId;
    private LocationRequest location;
}