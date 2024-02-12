package com.andresantiago.vendorsservice.stubs;

import com.andresantiago.vendorsservice.api.rest.v1.request.CreateVendorRequest;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;

public class CreateVendorRequestStub {

    public static CreateVendorRequest createAValidRequest() {
        return CreateVendorRequest.builder()
                .name("New Vendor")
                .taxId("1")
                .location(LocationStub.createRequest("Capivari do Sul", "RS"))
                .services(ServiceStub.createList(ServiceStub.createDto(ServiceCategoryEnum.AIR_CONDITIONING, true)))
                .build();

    }
}
