package com.andresantiago.vendorsservice.api;

import com.andresantiago.vendorsservice.api.request.CreateVendorRequest;
import com.andresantiago.vendorsservice.api.request.LocationRequest;
import com.andresantiago.vendorsservice.dto.VendorsStatisticsDto;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.repository.VendorDatabaseInMemory;
import com.andresantiago.vendorsservice.service.VendorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/vendors")
@RequiredArgsConstructor
public class VendorsApi {

    private final VendorService vendorService;
    private final VendorDatabaseInMemory vendorDatabaseInMemory;

    @GetMapping
    public ResponseEntity<Object> getAllVendors() {
        log.info("Getting all vendors...");
        final List<VendorEntity> vendors = vendorService.findAllVendors();
        log.info("Vendors got with success.");
        return ResponseEntity.ok().body(vendors);
    }

    @GetMapping("/jobs")
    public ResponseEntity<Object> getVendorByJob(@RequestParam String locationName,
                                                 @RequestParam String locationState,
                                                 @RequestParam ServiceCategoryEnum service) {
        LocationRequest locationRequest = LocationRequest.builder()
                .name(locationName)
                .state(locationState)
                .build();
        List<VendorEntity> vendor = vendorService.findVendorByJob(locationRequest, service);
        log.info("Vendor got with success.");
        return ResponseEntity.ok().body(vendor);
    }

    @GetMapping("/jobs/statistics")
    public ResponseEntity<Object> getVendorsStatisticsByJob(@RequestParam String locationName,
                                                            @RequestParam String locationState,
                                                            @RequestParam ServiceCategoryEnum service) {
        LocationRequest locationRequest = LocationRequest.builder()
                .name(locationName)
                .state(locationState)
                .build();
        VendorsStatisticsDto vendorStatistics = vendorService.getVendorsStatisticsByJob(locationRequest, service);
        log.info("Vendor got with success.");
        return ResponseEntity.ok().body(vendorStatistics);
    }

    @PostMapping
    public ResponseEntity<Object> createVendor(@RequestBody CreateVendorRequest request) {
        log.info("Creating a vendor with the request: {}", request);
        vendorService.createVendor(request);
        String test = "Vendor Created with Success";
        return ResponseEntity.ok().body(test);
    }

    @PutMapping("/jobs")
    public ResponseEntity<Void> includeJob(@RequestParam String taxId,
                                           @RequestParam ServiceCategoryEnum serviceCategoriesEnum) {
        log.info("Including a new service to the vendor taxId: {}", taxId);
        vendorService.includeService(taxId, serviceCategoriesEnum);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/compliance")
    public ResponseEntity<Void> updateCompliance(@RequestParam String taxId,
                                           @RequestParam boolean compliance) {
        log.info("Updating compliance...");
        vendorService.updateCompliance(taxId, compliance);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/taxId")
    public ResponseEntity<Object> getVendorByTaxId(@RequestParam String taxId) {
        log.info("Getting vendor by taxId: {}", taxId);
        VendorEntity vendor = vendorService.findVendorByTaxId(taxId);
        log.info("Vendor got with success.");
        return ResponseEntity.ok().body(vendor);
    }

    @GetMapping("/location")
    public ResponseEntity<Object> getVendorByLocation(@RequestParam String locationName,
                                                      @RequestParam String locationState) {
        LocationRequest locationRequest = LocationRequest.builder()
                .name(locationName)
                .state(locationState)
                .build();
        List<VendorEntity> vendor = vendorService.findVendorsByLocation(locationRequest);
        log.info("Vendor got with success.");
        return ResponseEntity.ok().body(vendor);
    }

    @PostMapping("/database")
    public ResponseEntity<List<VendorEntity>> createVendorDatabaseInMemory() {
        log.info("Creating vendor in memory.");
        vendorDatabaseInMemory.createVendorsData();
        log.info("Vendors created with success.");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/database")
    public ResponseEntity<List<VendorEntity>> dropVendorDatabaseInMemory() {
        log.info("Creating vendor in memory.");
        vendorDatabaseInMemory.eraseInMemoryData();
        log.info("Vendors created with success.");
        return ResponseEntity.ok().build();
    }
}