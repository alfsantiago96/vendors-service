package com.andresantiago.vendorsservice.service;

import com.andresantiago.vendorsservice.api.request.CreateVendorRequest;
import com.andresantiago.vendorsservice.api.request.LocationRequest;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoriesEnum;
import com.andresantiago.vendorsservice.mapper.VendorEntityMapper;
import com.andresantiago.vendorsservice.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;
    private final JobService jobService;
    private final MongoTemplate mongoTemplate;

    public void createVendor(CreateVendorRequest createVendorRequest) {
        log.info("Saving new Vendor... taxId: {}", createVendorRequest.getTaxId());

        final VendorEntity vendorEntity = VendorEntityMapper.map(createVendorRequest);
        vendorRepository.save(vendorEntity);

        log.info("Vendor saved with success.");
    }


    public void includeService(String taxId, ServiceCategoriesEnum service) {
        log.info("Including service to vendor. taxId: {}, service: {}", taxId, service);
        VendorEntity vendor = vendorRepository.findByTaxId(taxId);
        vendor.addService(service);
        VendorEntity updatedVendor = vendorRepository.save(vendor);
        log.info("Job Include with success. Jobs: {}", updatedVendor.getServices());
    }

    public VendorEntity findVendorByTaxId(String taxId) {
        VendorEntity vendor = vendorRepository.findByTaxId(taxId);
        log.info("Vendor return with success. vendor={}", vendor);
        return vendor;
    }

    public List<VendorEntity> findVendorByJob(LocationRequest locationRequest,
                                              ServiceCategoriesEnum serviceCategoriesEnum) {
        log.info("Searching vendors for a Job={}, Location={}", serviceCategoriesEnum, locationRequest);
        List<VendorEntity> vendorsByLocation = findVendorsByLocation(locationRequest);
        List<VendorEntity> vendorEntities = filterVendorsByService(vendorsByLocation, serviceCategoriesEnum);
        log.info("Vendors result for job={}", vendorEntities);
        return vendorEntities;
    }


    public List<VendorEntity> findVendorsByLocation(LocationRequest locationRequest) {
        Query query = new Query();
        query.addCriteria(Criteria.where("location.name").is(locationRequest.getName()));
        query.addCriteria(Criteria.where("location.state").is(locationRequest.getState()));

        List<VendorEntity> vendorsByLocation = mongoTemplate.find(query, VendorEntity.class);
        log.info("Vendors result by location={}", vendorsByLocation);
        return vendorsByLocation;
    }

    public List<VendorEntity> filterVendorsByService(List<VendorEntity> vendors, ServiceCategoriesEnum serviceCategoriesEnum) {
        return vendors.stream()
                .filter(vendorEntity -> filterServices(serviceCategoriesEnum, vendorEntity.getServices()))
                .sorted((o1, o2) -> Boolean.compare(o2.isCompliant(), o1.isCompliant()))
                .toList();
    }

    private boolean filterServices(ServiceCategoriesEnum serviceCategoriesEnum, List<ServiceCategoriesEnum> serviceList) {
        return serviceList.stream()
                .anyMatch(serviceCategoriesEnum1 -> serviceCategoriesEnum1.equals(serviceCategoriesEnum));
    }
}