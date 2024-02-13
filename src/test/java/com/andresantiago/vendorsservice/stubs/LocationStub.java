package com.andresantiago.vendorsservice.stubs;

import com.andresantiago.vendorsservice.api.rest.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.entity.LocationEntity;

public class LocationStub {

    public static LocationRequest createRequest(String name, String state) {
        return LocationRequest.builder()
                .name(name)
                .state(state)
                .build();
    }

    public static LocationEntity createEntity(String name, String state) {
        return LocationEntity.builder()
                .name(name)
                .state(state)
                .build();
    }
}
