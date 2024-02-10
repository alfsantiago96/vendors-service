package com.andresantiago.vendorsservice.stubs;

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
}
