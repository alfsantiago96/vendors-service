package com.andresantiago.vendorsservice.stubs;

import com.andresantiago.vendorsservice.dto.VendorDto;
import com.andresantiago.vendorsservice.dto.VendorsStatisticsDto;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;

import java.util.List;

public class VendorStub {

    public static VendorEntity createEntity() {
        return VendorEntity.builder()
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

    public static List<VendorEntity> createVendorsForTesting() {
        VendorEntity vendor1 = VendorStub.createEntity("Vendor 1",
                "Porto Alegre", "RS",
                ServiceCategoryEnum.AIR_CONDITIONING, false);

        VendorEntity vendor2 = VendorStub.createEntity("Vendor 2",
                "Capivari do Sul", "RS",
                ServiceCategoryEnum.AIR_CONDITIONING, false);

        VendorEntity vendor3 = VendorStub.createEntity("Vendor 3",
                "Capivari do Sul", "RS",
                ServiceCategoryEnum.AIR_CONDITIONING, true);

        VendorEntity vendor4 = VendorStub.createEntity("Vendor 4",
                "Capivari do Sul", "RS",
                ServiceCategoryEnum.LANDSCAPING, true);

        VendorEntity vendor5 = VendorStub.createEntity("Vendor 5",
                "Capivari do Sul", "RS",
                ServiceCategoryEnum.AIR_CONDITIONING, true);

        VendorEntity vendor6 = VendorStub.createEntity("Vendor 6",
                "Rio de Janeiro", "RJ",
                ServiceCategoryEnum.AIR_CONDITIONING, true);

        return List.of(
                vendor1,
                vendor2,
                vendor3,
                vendor4,
                vendor5,
                vendor6
        );
    }
}