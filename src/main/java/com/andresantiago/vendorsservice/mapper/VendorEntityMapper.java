package com.andresantiago.vendorsservice.mapper;

import com.andresantiago.vendorsservice.api.request.CreateVendorRequest;
import com.andresantiago.vendorsservice.api.request.LocationRequest;
import com.andresantiago.vendorsservice.entity.LocationEntity;
import com.andresantiago.vendorsservice.entity.VendorEntity;

public class VendorEntityMapper {

    public static VendorEntity map(CreateVendorRequest request) {
        return VendorEntity.builder()
                .name(request.getName())
                .taxId(request.getTaxId())
                .location(mapLocation(request.getLocation()))
                .build();
    }


    private static LocationEntity mapLocation(LocationRequest request) {
        return LocationEntity.builder()
                .name(request.getName())
                .state(request.getState())
                .build();
    }
}