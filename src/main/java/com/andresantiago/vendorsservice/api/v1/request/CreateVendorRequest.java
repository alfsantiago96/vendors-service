package com.andresantiago.vendorsservice.api.v1.request;

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