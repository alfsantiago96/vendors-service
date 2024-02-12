package com.andresantiago.vendorsservice.repository;

import com.andresantiago.vendorsservice.entity.LocationEntity;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.exception.BusinessException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class VendorDatabaseInMemory {

    private List<VendorEntity> vendors = new ArrayList<VendorEntity>();

    public void createVendor(VendorEntity vendor) {
        boolean matchAnyVendorTaxId = vendors.stream().anyMatch(vendor1 -> vendor1.getTaxId().equals(vendor.getTaxId()));
        if (matchAnyVendorTaxId) {
            throw new BusinessException("Vendor already registered.");
        }
        vendors.add(vendor);
    }

    public List<VendorEntity> getVendors() {
        return vendors;
    }

    public VendorEntity getVendorByTaxId(String taxId) {
        Optional<VendorEntity> first = vendors.stream()
                .filter(vendorEntity -> vendorEntity.getTaxId().equals(taxId))
                .findFirst();

        if (first.isEmpty()) {
            throw new BusinessException("Vendor not found");
        }
        return first.get();
    }

    public VendorEntity updateVendor(VendorEntity vendor) {
        return null;
    }

    @PostConstruct
    public void createVendorsData() {
        log.info("Creating vendors database...");
        final VendorEntity vendor1 = VendorEntity.builder()
                .name("Vendor 1")
                .taxId("1")
                .location(buildLocation("Fayette", "TX"))
                .build();

        final VendorEntity vendor2 = VendorEntity.builder()
                .name("Vendor 2")
                .taxId("2")
                .location(buildLocation("Fayette", "TX"))
                .build();

        final VendorEntity vendor3 = VendorEntity.builder()
                .name("Vendor 3")
                .taxId("3")
                .location(buildLocation("Fayette", "TX"))
                .build();

        final VendorEntity vendor4 = VendorEntity.builder()
                .name("Vendor 4")
                .taxId("4")
                .location(buildLocation("Glades", "FL"))
                .build();

        final VendorEntity vendor5 = VendorEntity.builder()
                .name("Vendor 5")
                .taxId("5")
                .location(buildLocation("Glades", "FL"))
                .build();

        final VendorEntity vendor6 = VendorEntity.builder()
                .name("Vendor 6")
                .taxId("6")
                .location(buildLocation("Glades", "FL"))
                .build();

        vendor1.addService(ServiceCategoryEnum.LANDSCAPING_MAINTENANCE, false);
        vendor1.addService(ServiceCategoryEnum.AIR_CONDITIONING, false);

        vendor2.addService(ServiceCategoryEnum.AIR_CONDITIONING, false);
        vendor3.addService(ServiceCategoryEnum.AIR_CONDITIONING, true);
        vendor4.addService(ServiceCategoryEnum.LANDSCAPING_MAINTENANCE, true);
        vendor5.addService(ServiceCategoryEnum.AIR_CONDITIONING, false);
        vendor6.addService(ServiceCategoryEnum.AIR_CONDITIONING, true);


        vendors.add(vendor1);
        vendors.add(vendor2);
        vendors.add(vendor3);
        vendors.add(vendor4);
        vendors.add(vendor5);
        vendors.add(vendor6);
    }

    public void eraseInMemoryData() {
        vendors = new ArrayList<>();
    }

    private LocationEntity buildLocation(String name, String state) {
        return LocationEntity.builder()
                .name(name)
                .state(state)
                .build();
    }
}