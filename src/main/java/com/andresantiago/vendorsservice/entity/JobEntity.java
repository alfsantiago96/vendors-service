package com.andresantiago.vendorsservice.entity;

import com.andresantiago.vendorsservice.enums.ServiceCategoriesEnum;
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
    private ServiceCategoriesEnum category;
    private LocationEntity location;
}