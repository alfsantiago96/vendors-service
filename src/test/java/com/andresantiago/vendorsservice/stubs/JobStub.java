package com.andresantiago.vendorsservice.stubs;

import com.andresantiago.vendorsservice.dto.JobDto;
import com.andresantiago.vendorsservice.entity.JobEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;

import java.time.LocalDateTime;

public class JobStub {

    public static JobEntity createEntity() {
        return JobEntity.builder()
                .id("id")
                .companyTaxId("999")
                .service(ServiceCategoryEnum.AIR_CONDITIONING)
                .location(LocationStub.createRequest("Capivari do Sul", "RS"))
                .vendor(VendorStub.createEntity())
                .build();
    }

    public static JobDto createDto() {
        return JobDto.builder()
                .id("id")
                .companyTaxId("999")
                .vendorName("Vendor 1")
                .vendorTaxId("1")
                .serviceCategory(ServiceCategoryEnum.AIR_CONDITIONING)
                .locationRequest(LocationStub.createRequest("Capivari do Sul", "RS"))
                .hireDate(LocalDateTime.of(2024, 1, 1, 0,0,0))
                .build();
    }
}
