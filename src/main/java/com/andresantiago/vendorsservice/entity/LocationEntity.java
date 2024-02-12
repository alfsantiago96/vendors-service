package com.andresantiago.vendorsservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class LocationEntity {

    private String id;
    private String name;
    private String state;
}