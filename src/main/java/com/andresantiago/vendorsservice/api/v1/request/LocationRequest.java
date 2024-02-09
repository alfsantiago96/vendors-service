package com.andresantiago.vendorsservice.api.v1.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String state;
}