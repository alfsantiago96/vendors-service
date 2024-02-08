package com.andresantiago.vendorsservice.api;

import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.repository.VendorDatabaseInMemory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/utils")
@RequiredArgsConstructor
public class DevUtilsApi {

    private final VendorDatabaseInMemory vendorDatabaseInMemory;

    @PostMapping("/database")
    public ResponseEntity<List<VendorEntity>> createVendorDatabaseInMemory() {
        log.info("Creating vendor in memory.");
        vendorDatabaseInMemory.createVendorsData();
        log.info("Vendors created with success.");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/database")
    public ResponseEntity<Object> dropVendorDatabaseInMemory() {
        log.info("Deleting vendor database in memory");
        vendorDatabaseInMemory.eraseInMemoryData();
        log.info("Vendors database deleted.");
        return ResponseEntity.ok().build();
    }
}