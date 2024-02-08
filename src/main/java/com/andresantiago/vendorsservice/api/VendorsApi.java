package com.andresantiago.vendorsservice.api;

import com.andresantiago.vendorsservice.api.request.CreateVendorRequest;
import com.andresantiago.vendorsservice.api.request.LocationRequest;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoriesEnum;
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

    @PostMapping
    public ResponseEntity<Object> createVendor(@RequestBody CreateVendorRequest request) {
        log.info("Creating a vendor with the request: {}", request);
        vendorService.createVendor(request);
        String test = "Vendor Created with Success";
        return ResponseEntity.ok().body(test);
    }

    @PutMapping
    public ResponseEntity<Object> includeJob(@RequestParam String taxId,
                                             @RequestParam ServiceCategoriesEnum serviceCategoriesEnum) {
        log.info("Including a new service to the vendor taxId: {}", taxId);
        vendorService.includeService(taxId, serviceCategoriesEnum);
        String test = "Service added with Success";
        return ResponseEntity.ok().body(test);
    }

    @GetMapping("/taxId")
    public ResponseEntity<Object> getVendorByTaxId(@RequestParam String taxId) {
        log.info("Getting vendor by taxId: {}", taxId);
        VendorEntity vendor = vendorService.findVendorByTaxId(taxId);
        log.info("Vendor got with success.");
        return ResponseEntity.ok().body(vendor);
    }

    @GetMapping("/localtion")
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

    @GetMapping("/job")
    public ResponseEntity<Object> getVendorByJob(@RequestParam String locationName,
                                                 @RequestParam String locationState,
                                                 @RequestParam ServiceCategoriesEnum service) {
        LocationRequest locationRequest = LocationRequest.builder()
                .name(locationName)
                .state(locationState)
                .build();
        List<VendorEntity> vendor = vendorService.findVendorByJob(locationRequest, service);
        log.info("Vendor got with success.");
        return ResponseEntity.ok().body(vendor);
    }
}