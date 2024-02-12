package com.andresantiago.vendorsservice.service;

import com.andresantiago.vendorsservice.api.rest.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.dto.JobDto;
import com.andresantiago.vendorsservice.entity.JobEntity;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.exception.BusinessException;
import com.andresantiago.vendorsservice.repository.JobDatabaseInMemory;
import com.andresantiago.vendorsservice.stubs.JobStub;
import com.andresantiago.vendorsservice.stubs.LocationStub;
import com.andresantiago.vendorsservice.stubs.VendorStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

    @Mock
    private JobDatabaseInMemory jobDatabaseInMemory;

    @Mock
    private VendorService vendorService;

    @InjectMocks
    private JobService jobService;


    @Test
    void shouldHireAJob_whenVendorIsEligible() {
        VendorEntity eligibleVendor = VendorStub.createEntity();
        ServiceCategoryEnum serviceCategory = ServiceCategoryEnum.AIR_CONDITIONING;
        String vendorTaxId = "1";
        String companyTaxId = "999";
        LocationRequest locationRequest = LocationStub.createRequest("Capivari do Sul", "RS");

        when(vendorService.findVendorByTaxId(eq(vendorTaxId)))
                .thenReturn(eligibleVendor);
        doNothing().when(jobDatabaseInMemory).create(any(JobEntity.class));

        jobService.hireAJob(serviceCategory, vendorTaxId, companyTaxId, locationRequest);

        verify(vendorService, times(1)).findVendorByTaxId(eq(vendorTaxId));
        verify(jobDatabaseInMemory, times(1)).create(any(JobEntity.class));
    }

    @Test
    void shouldNotHireAJob_whenVendorIsNotEligible() {
        VendorEntity eligibleVendor = VendorStub.createEntity();
        ServiceCategoryEnum serviceCategory = ServiceCategoryEnum.LANDSCAPING_MAINTENANCE;
        String vendorTaxId = "1";
        String companyTaxId = "999";
        LocationRequest locationRequest = LocationStub.createRequest("Capivari do Sul", "RS");

        when(vendorService.findVendorByTaxId(eq(vendorTaxId)))
                .thenReturn(eligibleVendor);

        assertThrows(BusinessException.class,
                () -> jobService.hireAJob(serviceCategory, vendorTaxId, companyTaxId, locationRequest));

        verify(vendorService, times(1)).findVendorByTaxId(eq(vendorTaxId));
        verify(jobDatabaseInMemory, never()).create(any(JobEntity.class));
    }

    @Test
    void shouldReturnJobsFromCompanyWithSuccess_givenAValidTaxId() {
        String companyTaxId = "999";
        List<JobEntity> jobEntityList = List.of(JobStub.createEntity());

        when(jobDatabaseInMemory.findJobsByCompany(eq(companyTaxId)))
                .thenReturn(jobEntityList);

        List<JobDto> jobsByCompany = jobService.findJobsByCompany(companyTaxId);

        assertNotNull(jobEntityList);
        JobDto jobDto = jobsByCompany.get(0);
        assertEquals("id", jobDto.getId());
        assertEquals("Vendor 1", jobDto.getVendorName());
        assertEquals("1", jobDto.getVendorTaxId());
        assertEquals(ServiceCategoryEnum.AIR_CONDITIONING, jobDto.getServiceCategory());
        assertEquals("Capivari do Sul", jobDto.getLocationRequest().getName());
        assertEquals("RS", jobDto.getLocationRequest().getState());
        assertNotNull(jobDto.getHireDate());
    }

    @Test
    void shouldReturnJobsFromVendorWithSuccess_givenAValidTaxId() {
        String vendorTaxId = "1";
        List<JobEntity> jobEntityList = List.of(JobStub.createEntity());

        when(jobDatabaseInMemory.findJobsByVendor(eq(vendorTaxId)))
                .thenReturn(jobEntityList);

        List<JobDto> jobsByVendor = jobService.findJobsByVendor(vendorTaxId);

        assertNotNull(jobEntityList);
        JobDto jobDto = jobsByVendor.get(0);
        assertEquals("id", jobDto.getId());
        assertEquals("Vendor 1", jobDto.getVendorName());
        assertEquals("1", jobDto.getVendorTaxId());
        assertEquals(ServiceCategoryEnum.AIR_CONDITIONING, jobDto.getServiceCategory());
        assertEquals("Capivari do Sul", jobDto.getLocationRequest().getName());
        assertEquals("RS", jobDto.getLocationRequest().getState());
        assertNotNull(jobDto.getHireDate());
    }

    @Test
    void shouldReturnAEmptyList_whenVendorDoesNotHaveJobs_givenAValidTaxId() {
        String vendorTaxId = "1";
        List<JobEntity> jobEntityList = new ArrayList<>();

        when(jobDatabaseInMemory.findJobsByVendor(eq(vendorTaxId)))
                .thenReturn(jobEntityList);

        List<JobDto> jobsByVendor = jobService.findJobsByVendor(vendorTaxId);

        assertNotNull(jobEntityList);
        assertTrue(jobsByVendor.isEmpty());
    }

    @Test
    void shouldReturnAEmptyList_whenCompanyDoesNotHaveJobs_givenAValidTaxId() {
        String companyTaxId = "1";
        List<JobEntity> jobEntityList = new ArrayList<>();

        when(jobDatabaseInMemory.findJobsByCompany(eq(companyTaxId)))
                .thenReturn(jobEntityList);

        List<JobDto> jobsByCompany = jobService.findJobsByCompany(companyTaxId);

        assertNotNull(jobEntityList);
        assertTrue(jobsByCompany.isEmpty());
    }
}