package com.andresantiago.vendorsservice.api.v1;

import com.andresantiago.vendorsservice.api.v1.request.CreateVendorRequest;
import com.andresantiago.vendorsservice.api.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.dto.VendorDto;
import com.andresantiago.vendorsservice.dto.VendorsStatisticsDto;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.service.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/vendors")
@RequiredArgsConstructor
@Tag(name = "1. Vendors", description = "Vendors searching and registration")
@SecurityRequirement(name = "basicAuth")
public class VendorsApi {

    private final VendorService vendorService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/jobs")
    @Operation(description = "Get potential vendors for a Job.")
    public ResponseEntity<Object> getPotentialVendorsByJob(@RequestParam String locationName,
                                                           @RequestParam String locationState,
                                                           @RequestParam ServiceCategoryEnum service) {
        LocationRequest locationRequest = LocationRequest.builder()
                .name(locationName)
                .state(locationState)
                .build();
        List<VendorDto> vendor = vendorService.findPotentialVendorsByJob(locationRequest, service);
        log.info("Vendor got with success.");
        return ResponseEntity.ok().body(vendor);
    }

    @GetMapping("/jobs/statistics")
    @Operation(description = "Get vendors statistics by Service and Location")
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

    @PreAuthorize("hasRole('ROLE_VENDOR_CREATOR')")
    @PostMapping
    @Operation(description = "Creates a vendor")
    public ResponseEntity<Object> createVendor(@RequestBody @Valid CreateVendorRequest request) {
        log.info("Creating a vendor with the request: {}", request);
        vendorService.createVendor(request);
        String test = "Vendor Created with Success";
        return ResponseEntity.ok().body(test);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{taxId}/jobs")
    @Operation(description = "Include a new service to a Vendor")
    public ResponseEntity<Void> includeJob(@PathVariable String taxId,
                                           @RequestParam ServiceCategoryEnum serviceCategoriesEnum,
                                           @RequestParam boolean isCompliant) {
        log.info("Including a new service to the vendor taxId: {}", taxId);
        vendorService.includeService(taxId, serviceCategoriesEnum, isCompliant);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("{taxId}/compliance")
    @Operation(description = "Update compliance status for a Vendor")
    public ResponseEntity<Void> updateCompliance(@PathVariable String taxId,
                                                 @RequestParam ServiceCategoryEnum serviceCategoryEnum,
                                                 @RequestParam boolean isCompliant) {
        log.info("Updating compliance...");
        vendorService.updateCompliance(taxId, serviceCategoryEnum, isCompliant);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{taxId}")
    @Operation(description = "Get a Vendor by taxId")
    public ResponseEntity<Object> getVendorByTaxId(@PathVariable String taxId) {
        log.info("Getting vendor by taxId: {}", taxId);
        VendorEntity vendor = vendorService.findVendorByTaxId(taxId);
        log.info("Vendor got with success.");
        return ResponseEntity.ok().body(vendor);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/location")
    @Operation(description = "Get all vendors given the Location.", hidden = true)
    public ResponseEntity<Object> getVendorsByLocation(@RequestParam String locationName,
                                                       @RequestParam String locationState) {
        LocationRequest locationRequest = LocationRequest.builder()
                .name(locationName)
                .state(locationState)
                .build();
        List<VendorEntity> vendor = vendorService.findVendorsByLocation(locationRequest);
        log.info("Vendor got with success.");
        return ResponseEntity.ok().body(vendor);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(description = "Get all vendors from database.", hidden = true)
    public ResponseEntity<Object> getAllVendors() {
        log.info("Getting all vendors...");
        final List<VendorEntity> vendors = vendorService.findAllVendors();
        log.info("Vendors got with success.");
        return ResponseEntity.ok().body(vendors);
    }
}