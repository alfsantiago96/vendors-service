package com.andresantiago.vendorsservice.api.request;

import com.andresantiago.vendorsservice.entity.LocationEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobRequest {

    private String id;
    private ServiceCategoryEnum category;
    private LocationEntity location;
}