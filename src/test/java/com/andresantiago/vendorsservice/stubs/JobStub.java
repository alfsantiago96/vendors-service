package com.andresantiago.vendorsservice.stubs;

import com.andresantiago.vendorsservice.entity.JobEntity;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import org.springframework.boot.autoconfigure.batch.BatchProperties;

import java.time.LocalDateTime;

public class JobStub {

    public static JobEntity create() {
        return JobEntity.builder()
                .id("id")
                .companyTaxId("999")
                .service(ServiceCategoryEnum.AIR_CONDITIONING)
                .location(LocationStub.createRequest("Capivari do Sul", "RS"))
                .vendor(VendorStub.createEntity())
                .hireDate(LocalDateTime.of(2024, 1, 1, 0,0,0))
                .build();
    }
}
