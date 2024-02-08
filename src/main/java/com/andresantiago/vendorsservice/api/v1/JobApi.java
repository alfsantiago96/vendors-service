package com.andresantiago.vendorsservice.api.v1;

import com.andresantiago.vendorsservice.api.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.entity.JobEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.service.JobService;
import com.andresantiago.vendorsservice.service.VendorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/jobs")
@RequiredArgsConstructor
public class JobApi {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<Object> createJob(@RequestParam String companyTaxId,
                                            @RequestParam ServiceCategoryEnum serviceCategory,
                                            @RequestParam String vendorTaxId,
                                            @RequestParam String locationName,
                                            @RequestParam String locationState) {
        log.info("Creating a job for the vendor taxId: {}", vendorTaxId);
        LocationRequest locationRequest = LocationRequest.builder().name(locationName).state(locationState).build();
        jobService.hireAJob(serviceCategory, vendorTaxId, companyTaxId, locationRequest);
        log.info("Job created with success");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/company")
    public ResponseEntity<List<JobEntity>> getJobByCompany(String taxId) {
        log.info("Getting the jobs from company taxId: {}", taxId);
        List<JobEntity> jobsByCompany = jobService.findJobsByCompany(taxId);
        log.info("Job got with success");
        return ResponseEntity.ok().body(jobsByCompany);
    }

    @GetMapping("/vendor")
    public ResponseEntity<List<JobEntity>> getJobByVendor(String taxId) {
        log.info("Getting the jobs from vendor taxId: {}", taxId);
        List<JobEntity> jobsByCompany = jobService.findJobsByVendor(taxId);
        log.info("Job got with success");
        return ResponseEntity.ok().body(jobsByCompany);
    }
}