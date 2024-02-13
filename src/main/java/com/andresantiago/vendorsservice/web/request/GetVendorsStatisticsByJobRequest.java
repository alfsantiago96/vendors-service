package com.andresantiago.vendorsservice.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class GetVendorsStatisticsByJobRequest {

    private String locationName;
    private String locationState;
    private String serviceCategory;
}