package com.andresantiago.vendorsservice.mapper;

import com.andresantiago.vendorsservice.api.request.CreateJobRequest;
import com.andresantiago.vendorsservice.entity.JobEntity;

public class JobEntityMapper {

    public static JobEntity map(CreateJobRequest request) {
        return JobEntity.builder()
                .category(request.getCategory())
                .location(request.getLocation())
                .build();
    }
}