package com.andresantiago.vendorsservice.api.v1;

import com.andresantiago.vendorsservice.api.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.dto.JobDto;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/jobs")
@RequiredArgsConstructor
@Tag(name = "jobs-api", description = "Job hiring and consulting")
@SecurityRequirement(name = "basicAuth")
public class JobApi {

    private final JobService jobService;

    @PreAuthorize("hasRole('ROLE_JOB_CREATOR')")
    @PostMapping
    @Operation(description = "Creates a hire intention for a Job with the provide information.")
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/company")
    @Operation(description = "Search for registered Jobs with the provided company.")
    public ResponseEntity<List<JobDto>> getJobByCompany(String taxId) {
        log.info("Getting the jobs from company taxId: {}", taxId);
        List<JobDto> jobsByCompany = jobService.findJobsByCompany(taxId);
        log.info("Job got with success");
        return ResponseEntity.ok().body(jobsByCompany);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/vendor")
    @Operation(description = "Search for registered Jobs with the provided vendor.")
    public ResponseEntity<List<JobDto>> getJobByVendor(String taxId) {
        log.info("Getting the jobs from vendor taxId: {}", taxId);
        List<JobDto> jobsByCompany = jobService.findJobsByVendor(taxId);
        log.info("Job got with success");
        return ResponseEntity.ok().body(jobsByCompany);
    }
}