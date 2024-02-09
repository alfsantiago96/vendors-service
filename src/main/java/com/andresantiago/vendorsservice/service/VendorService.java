package com.andresantiago.vendorsservice.service;

import com.andresantiago.vendorsservice.api.v1.request.CreateVendorRequest;
import com.andresantiago.vendorsservice.api.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.dto.ServiceDto;
import com.andresantiago.vendorsservice.dto.VendorDto;
import com.andresantiago.vendorsservice.dto.VendorsStatisticsDto;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.exception.BusinessException;
import com.andresantiago.vendorsservice.mapper.VendorDtoMapper;
import com.andresantiago.vendorsservice.mapper.VendorEntityMapper;
import com.andresantiago.vendorsservice.repository.VendorDatabaseInMemory;
import com.andresantiago.vendorsservice.validation.VendorValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorDatabaseInMemory vendorDatabaseInMemory;

    public VendorsStatisticsDto getVendorsStatisticsByJob(LocationRequest request, ServiceCategoryEnum serviceCategory) {
        log.info("Finding vendors statistics for the given service. {}", serviceCategory);
        List<VendorDto> vendorByJob = findPotentialVendorsByJob(request, serviceCategory);
        int totalVendors = vendorByJob.size();
        int compliantVendors = (int) vendorByJob.stream().filter(VendorDto::isCompliant).count();
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

    public void createVendor(CreateVendorRequest createVendorRequest) {
        log.info("Creating new Vendor. taxId: {}", createVendorRequest.getTaxId());

        final VendorEntity vendorEntity = VendorEntityMapper.map(createVendorRequest);
        vendorDatabaseInMemory.createVendor(vendorEntity);

        log.info("Vendor created with success.");
    }

    public List<VendorDto> findPotentialVendorsByJob(LocationRequest locationRequest,
                                                     ServiceCategoryEnum serviceCategoriesEnum) {
        log.info("Searching vendors for a Job={}, Location={}", serviceCategoriesEnum, locationRequest);
        List<VendorEntity> vendorsByLocation = findVendorsByLocation(locationRequest);
        List<VendorEntity> vendorFiltered = filterVendorsByService(vendorsByLocation, serviceCategoriesEnum);
        List<VendorDto> vendorsSorted = sortVendorsByCompliance(vendorFiltered, serviceCategoriesEnum);
        log.info("Vendors result for job={}", vendorsSorted);
        return vendorsSorted;
    }

    private List<VendorDto> sortVendorsByCompliance(List<VendorEntity> vendorsFiltered, ServiceCategoryEnum serviceCategoryEnum) {
        List<VendorDto> vendorDtoList = new ArrayList<>();

        for (VendorEntity vendor : vendorsFiltered) {
            boolean isCompliant = VendorValidation.isCompliantByService(serviceCategoryEnum, vendor);
            VendorDto vendorDto = VendorDtoMapper.map(vendor, isCompliant, serviceCategoryEnum);
            vendorDtoList.add(vendorDto);
        }

        return vendorDtoList.stream()
                .sorted((o1, o2) -> Boolean.compare(o2.isCompliant(), o1.isCompliant()))
                .toList();
    }

    public void includeService(String taxId, ServiceCategoryEnum service, boolean isCompliant) {
        log.info("Including service to vendor. taxId: {}, service: {}", taxId, service);
        VendorEntity vendor = findVendorByTaxId(taxId);
        vendor.addService(service, isCompliant);
        log.info("Job Include with success. Jobs");
    }

    public void updateCompliance(String taxId, ServiceCategoryEnum serviceCategory, boolean isCompliant) {
        log.info("Updating compliance status for vendor taxId: {}", taxId);
        VendorEntity vendor = findVendorByTaxId(taxId);
        if (!VendorValidation.isOfferingTheService(vendor, serviceCategory)) {
            throw new BusinessException("Vendor does not offer this service.");
        }

        for (ServiceDto serviceDto : vendor.getServices()) {
            if (serviceDto.getServiceCategory().equals(serviceCategory)) {
                serviceDto.setCompliant(isCompliant);
            }
        }
        log.info("Vendor compliance updated with success.");
    }

    public List<VendorEntity> findAllVendors() {
        return vendorDatabaseInMemory.getVendors();
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
                .toList();
    }

    private boolean filterServices(ServiceCategoryEnum serviceCategoriesEnum, List<ServiceDto> serviceList) {
        return serviceList.stream()
                .anyMatch(serviceDto -> serviceDto.getServiceCategory().equals(serviceCategoriesEnum));
    }
}