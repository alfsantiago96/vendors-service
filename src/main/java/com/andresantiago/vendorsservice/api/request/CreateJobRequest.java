package com.andresantiago.vendorsservice.api.request;

import com.andresantiago.vendorsservice.entity.LocationEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoriesEnum;
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
    private ServiceCategoriesEnum category;
    private LocationEntity location;
}