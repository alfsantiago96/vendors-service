package com.andresantiago.vendorsservice.stubs;

import com.andresantiago.vendorsservice.dto.ServiceDto;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public class ServiceStub {

    public static ServiceDto createDto(ServiceCategoryEnum serviceCategory,
                                       boolean isCompliant) {
        return ServiceDto.builder()
                .serviceCategory(serviceCategory)
                .isCompliant(isCompliant)
                .build();
    }

    public static List<ServiceDto> createList(ServiceDto serviceDto) {
        List<ServiceDto> serviceDtoList = new ArrayList<>();
        serviceDtoList.add(serviceDto);
        return serviceDtoList;
    }
}
