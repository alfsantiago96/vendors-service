package com.andresantiago.vendorsservice.repository;

import com.andresantiago.vendorsservice.entity.JobEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoriesEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<JobEntity, String> {

    JobEntity findByCategory(ServiceCategoriesEnum serviceCategoriesEnum);
}