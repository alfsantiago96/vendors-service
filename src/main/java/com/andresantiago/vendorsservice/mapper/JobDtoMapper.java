package com.andresantiago.vendorsservice.mapper;

import com.andresantiago.vendorsservice.dto.JobDto;
import com.andresantiago.vendorsservice.entity.JobEntity;

public class JobDtoMapper {

    public static JobDto map(JobEntity jobEntity) {
        return JobDto.builder()
                .id(jobEntity.getId())
                .companyTaxId(jobEntity.getCompanyTaxId())
                .vendorName(jobEntity.getVendor().getName())
                .vendorTaxId(jobEntity.getVendor().getTaxId())
                .serviceCategory(jobEntity.getService())
                .locationRequest(jobEntity.getLocation())
                .build();
    }
}
