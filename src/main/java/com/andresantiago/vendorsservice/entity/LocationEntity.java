package com.andresantiago.vendorsservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationEntity {

    @Id
    private String id;
    private String name;
    private String state;
}