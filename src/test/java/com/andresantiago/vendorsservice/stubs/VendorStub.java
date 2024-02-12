package com.andresantiago.vendorsservice.stubs;

import com.andresantiago.vendorsservice.dto.VendorDto;
import com.andresantiago.vendorsservice.dto.VendorsStatisticsDto;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;

public class VendorStub {

    public static VendorEntity createEntity() {
        return VendorEntity.builder()
                .id("1")
                .name("Vendor 1")
                .taxId("1")
                .location(LocationStub.createEntity("Capivari do Sul", "RS"))
                .services(ServiceStub.createList(
                        ServiceStub.createDto(
                                ServiceCategoryEnum.AIR_CONDITIONING,
                                true)))
                .build();
    }

    public static VendorEntity createEntity(ServiceCategoryEnum serviceCategoryEnum, boolean isCompliant) {
        return VendorEntity.builder()
                .id("1")
                .name("Vendor 1")
                .taxId("1")
                .location(LocationStub.createEntity("Capivari do Sul", "RS"))
                .services(ServiceStub.createList(
                        ServiceStub.createDto(
                                serviceCategoryEnum,
                                isCompliant)))
                .build();
    }

    public static VendorEntity createEntity(String locationName, String locationState) {
        return VendorEntity.builder()
                .id("1")
                .name("Vendor 1")
                .taxId("1")
                .location(LocationStub.createEntity(locationName, locationState))
                .services(ServiceStub.createList(
                        ServiceStub.createDto(
                                ServiceCategoryEnum.AIR_CONDITIONING,
                                true)))
                .build();
    }

    public static VendorEntity createEntity(String vendorName, String locationName, String locationState,
                                            ServiceCategoryEnum serviceCategoryEnum, boolean isCompliant) {
        return VendorEntity.builder()
                .id("1")
                .name(vendorName)
                .taxId("1")
                .location(LocationStub.createEntity(locationName, locationState))
                .services(ServiceStub.createList(
                        ServiceStub.createDto(
                                serviceCategoryEnum,
                                isCompliant)))
                .build();
    }

    public static VendorEntity createEntityNotCompliant() {
        return VendorEntity.builder()
                .id("1")
                .name("Vendor 1")
                .taxId("1")
                .location(LocationStub.createEntity("Capivari do Sul", "RS"))
                .services(ServiceStub.createList(
                        ServiceStub.createDto(
                                ServiceCategoryEnum.AIR_CONDITIONING,
                                false)))
                .build();
    }

    public static VendorDto createDto() {
        return VendorDto.builder()
                .id("id")
                .name("Vendor 1")
                .taxId("1")
                .isCompliant(true)
                .location(LocationStub.createEntity("Capivari do Sul", "RS"))
                .service(ServiceCategoryEnum.AIR_CONDITIONING)
                .build();
    }

    public static VendorsStatisticsDto createVendorsStatisticsDto() {
        return VendorsStatisticsDto.builder()
                .serviceCategory(ServiceCategoryEnum.AIR_CONDITIONING)
                .location(LocationStub.createRequest("Capivari do Sul", "RS"))
                .totalVendors(5)
                .totalCompliant(3)
                .totalNotCompliant(2)
                .build();
    }
}
