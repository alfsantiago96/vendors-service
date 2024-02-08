package com.andresantiago.vendorsservice.entity;

import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobEntity {

    @Id
    private String id;
    private ServiceCategoryEnum category;
    private LocationEntity location;
}