package com.andresantiago.vendorsservice.service;

import com.andresantiago.vendorsservice.api.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.entity.JobEntity;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.repository.JobDatabaseInMemory;
import com.andresantiago.vendorsservice.validation.VendorValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final VendorService vendorService;
    private final JobDatabaseInMemory jobDatabaseInMemory;

    public void hireAJob(ServiceCategoryEnum serviceCategory, String vendorTaxId, String companyTaxId, LocationRequest locationRequest) {
        VendorEntity vendor = vendorService.findVendorByTaxId(vendorTaxId);
        VendorValidation.validateVendor(vendor, serviceCategory, locationRequest);
        JobEntity job = JobEntity.builder()
                .companyTaxId(companyTaxId)
                .service(serviceCategory)
                .vendor(vendor)
                .location(locationRequest)
                .build();
        jobDatabaseInMemory.create(job);
    }

    public List<JobEntity> findJobsByCompany(String taxId) {
        return jobDatabaseInMemory.findJobsByCompany(taxId);
    }

    public List<JobEntity> findJobsByVendor(String taxId) {
        return jobDatabaseInMemory.findJobsByVendor(taxId);
    }
}