package com.andresantiago.vendorsservice.api.v1;

import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.repository.VendorDatabaseInMemory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/database")
@RequiredArgsConstructor
@Tag(name = "3. Dev Utils", description = "To make easier evaluating the assessment.")
@SecurityRequirement(name = "basicAuth")
public class DevUtilsApi {

    private final VendorDatabaseInMemory vendorDatabaseInMemory;

    /* Dev purpose only */
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Create a database to test the assessment.")
    public ResponseEntity<List<VendorEntity>> createVendorDatabaseInMemory() {
        log.info("Creating vendor in memory.");
        vendorDatabaseInMemory.createVendorsData();
        log.info("Vendors created with success.");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /* Dev purpose only */
    @DeleteMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Clean vendor database.")
    public ResponseEntity<List<VendorEntity>> dropVendorDatabaseInMemory() {
        log.info("Deleting all vendors in memory.");
        vendorDatabaseInMemory.eraseInMemoryData();
        log.info("Vendors deleted with success.");
        return ResponseEntity.ok().build();
    }
}