package com.andresantiago.vendorsservice.api;

import com.andresantiago.vendorsservice.api.request.CreateJobRequest;
import com.andresantiago.vendorsservice.api.request.LocationRequest;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoriesEnum;
import com.andresantiago.vendorsservice.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
public class JobApi {

    private JobService jobService;
    @GetMapping
    public ResponseEntity<List<VendorEntity>> getVendorByServiceAndLocation(@RequestParam ServiceCategoriesEnum serviceCategoriesEnum,
                                                                            @RequestParam String locationName,
                                                                            @RequestParam String locationState) {

        LocationRequest location = LocationRequest.builder()
                .name(locationName)
                .state(locationState).build();
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Object> createJob(CreateJobRequest createJobRequest) {
        log.info("Starting a Job creation");
        return ResponseEntity.ok().body(CreateJobRequest.builder().category(ServiceCategoriesEnum.LANDSCAPING).build());
    }
}