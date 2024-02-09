package com.andresantiago.vendorsservice.repository;

import com.andresantiago.vendorsservice.entity.LocationEntity;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.exception.BusinessException;
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
            throw new RuntimeException("Vendor already registered.");
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

    public void createVendorsData() {
        log.info("Creating vendors database...");
        final VendorEntity vendor1 = VendorEntity.builder()
                .name("Vendor 1")
                .taxId("1")
                .isCompliant(false)
                .location(buildLocation("Fayette", "TX"))
                .services(List.of(
                        ServiceCategoryEnum.LANDSCAPING_MAINTENANCE,
                        ServiceCategoryEnum.AIR_CONDITIONING))
                .build();

        final VendorEntity vendor2 = VendorEntity.builder()
                .name("Vendor 2")
                .taxId("2")
                .isCompliant(false)
                .location(buildLocation("Fayette", "TX"))
                .services(List.of(
                        ServiceCategoryEnum.AIR_CONDITIONING))
                .build();

        final VendorEntity vendor3 = VendorEntity.builder()
                .name("Vendor 3")
                .taxId("3")
                .isCompliant(true)
                .location(buildLocation("Fayette", "TX"))
                .services(List.of(
                        ServiceCategoryEnum.AIR_CONDITIONING))
                .build();

        final VendorEntity vendor4 = VendorEntity.builder()
                .name("Vendor 4")
                .taxId("4")
                .isCompliant(true)
                .location(buildLocation("Glades", "FL"))
                .services(List.of(
                        ServiceCategoryEnum.LANDSCAPING_MAINTENANCE))
                .build();

        final VendorEntity vendor5 = VendorEntity.builder()
                .name("Vendor 5")
                .taxId("5")
                .isCompliant(false)
                .location(buildLocation("Glades", "FL"))
                .services(List.of(
                        ServiceCategoryEnum.AIR_CONDITIONING))
                .build();

        final VendorEntity vendor6 = VendorEntity.builder()
                .name("Vendor 6")
                .taxId("6")
                .isCompliant(true)
                .location(buildLocation("Glades", "FL"))
                .services(List.of(
                        ServiceCategoryEnum.AIR_CONDITIONING))
                .build();

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