package com.andresantiago.vendorsservice.repository;

import com.andresantiago.vendorsservice.entity.VendorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends MongoRepository<VendorEntity, String> {
    VendorEntity findByTaxId(String taxId);
}