package com.andresantiago.vendorsservice.dto;

import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {

    private ServiceCategoryEnum serviceCategory;
    private boolean isCompliant;
}