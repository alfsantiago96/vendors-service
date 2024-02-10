package com.andresantiago.vendorsservice.mapper;

import com.andresantiago.vendorsservice.dto.JobDto;
import com.andresantiago.vendorsservice.entity.JobEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.stubs.JobStub;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JobDtoMapperTest {

    @Test
    void shouldMapEntityToDtoWithSuccess() {
        final JobEntity jobEntity = JobStub.createEntity();

        final JobDto jobDto = JobDtoMapper.map(jobEntity);

        assertAll(
                () -> assertEquals("id", jobDto.getId()),
                () -> assertEquals("999", jobDto.getCompanyTaxId()),
                () -> assertEquals("Vendor 1", jobDto.getVendorName()),
                () -> assertEquals("1", jobDto.getVendorTaxId()),
                () -> assertEquals(ServiceCategoryEnum.AIR_CONDITIONING, jobDto.getServiceCategory()),
                () -> assertEquals("Capivari do Sul", jobDto.getLocationRequest().getName()),
                () -> assertEquals("RS", jobDto.getLocationRequest().getState()),
                () -> assertNotNull(jobDto.getHireDate())
        );
    }
}