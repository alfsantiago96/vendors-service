package com.andresantiago.vendorsservice.service;

import com.andresantiago.vendorsservice.api.request.CreateVendorRequest;
import com.andresantiago.vendorsservice.api.request.LocationRequest;
import com.andresantiago.vendorsservice.dto.VendorsStatisticsDto;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.mapper.VendorEntityMapper;
import com.andresantiago.vendorsservice.repository.VendorDatabaseInMemory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorDatabaseInMemory vendorDatabaseInMemory;

    public List<VendorEntity> findAllVendors() {
        return vendorDatabaseInMemory.getVendors();
    }

    public List<VendorEntity> findVendorByJob(LocationRequest locationRequest,
                                              ServiceCategoryEnum serviceCategoriesEnum) {
        log.info("Searching vendors for a Job={}, Location={}", serviceCategoriesEnum, locationRequest);
        List<VendorEntity> vendorsByLocation = findVendorsByLocation(locationRequest);
        List<VendorEntity> vendorEntities = filterVendorsByService(vendorsByLocation, serviceCategoriesEnum);
        log.info("Vendors result for job={}", vendorEntities);
        return vendorEntities;
    }

    public void createVendor(CreateVendorRequest createVendorRequest) {
        log.info("Creating new Vendor. taxId: {}", createVendorRequest.getTaxId());

        final VendorEntity vendorEntity = VendorEntityMapper.map(createVendorRequest);
        vendorDatabaseInMemory.createVendor(vendorEntity);

        log.info("Vendor created with success.");
    }


    public VendorsStatisticsDto getVendorsStatisticsByJob(LocationRequest request, ServiceCategoryEnum serviceCategory) {
        log.info("Finding vendors statistics for the given service. {}", serviceCategory);
        List<VendorEntity> vendorByJob = findVendorByJob(request, serviceCategory);
        int totalVendors = vendorByJob.size();
        int compliantVendors = (int) vendorByJob.stream().filter(VendorEntity::isCompliant).count();
        int notCompliantVendors = (int) vendorByJob.stream().filter(vendorEntity -> !vendorEntity.isCompliant()).count();

        log.info("Search success.");
        return VendorsStatisticsDto.builder()
                .serviceCategory(serviceCategory)
                .location(request)
                .totalVendors(totalVendors)
                .totalCompliant(compliantVendors)
                .totalNotCompliant(notCompliantVendors)
                .build();
    }

    public void includeService(String taxId, ServiceCategoryEnum service) {
        log.info("Including service to vendor. taxId: {}, service: {}", taxId, service);
        VendorEntity vendor = findVendorByTaxId(taxId);
        vendor.addService(service);
        VendorEntity updatedVendor = vendorDatabaseInMemory.getVendorByTaxId(taxId);
        log.info("Job Include with success. Jobs: {}", updatedVendor.getServices());
    }

    public void updateCompliance(String taxId, boolean isCompliant) {
        log.info("Updatating compliance status for vendor taxId: {}, to isCompliant: {}", taxId, isCompliant);
        VendorEntity vendor = findVendorByTaxId(taxId);
        vendor.setCompliant(isCompliant);
        log.info("Vendor compliance updated with success.");
    }

    public VendorEntity findVendorByTaxId(String taxId) {
        VendorEntity vendor = vendorDatabaseInMemory.getVendorByTaxId(taxId);
        log.info("Vendor return with success. vendor={}", vendor);
        return vendor;
    }


    public List<VendorEntity> findVendorsByLocation(LocationRequest locationRequest) {

        List<VendorEntity> vendorsByLocation = vendorDatabaseInMemory.getVendors().stream()
                .filter(vendorEntity ->
                        vendorEntity.getLocation().getName().equals(locationRequest.getName())
                                && vendorEntity.getLocation().getState().equals(locationRequest.getState()))
                .collect(Collectors.toList());

        log.info("Vendors result by location={}", vendorsByLocation);
        return vendorsByLocation;
    }

    public List<VendorEntity> filterVendorsByService(List<VendorEntity> vendors, ServiceCategoryEnum serviceCategoriesEnum) {
        return vendors.stream()
                .filter(vendorEntity -> filterServices(serviceCategoriesEnum, vendorEntity.getServices()))
                .sorted((o1, o2) -> Boolean.compare(o2.isCompliant(), o1.isCompliant()))
                .toList();
    }

    private boolean filterServices(ServiceCategoryEnum serviceCategoriesEnum, List<ServiceCategoryEnum> serviceList) {
        return serviceList.stream()
                .anyMatch(serviceCategoriesEnum1 -> serviceCategoriesEnum1.equals(serviceCategoriesEnum));
    }

}