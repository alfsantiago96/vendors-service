package com.andresantiago.vendorsservice.api.v1.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {

    @NotEmpty(message = "Location name cannot be empty")
    private String name;

    @NotEmpty(message = "Location state cannot be empty.")
    @Size(min = 2, max = 2, message = "Location state should have 2 characters.")
    private String state;
}