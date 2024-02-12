package com.andresantiago.vendorsservice.service;

import com.andresantiago.vendorsservice.api.rest.v1.request.CreateVendorRequest;
import com.andresantiago.vendorsservice.api.rest.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.dto.ServiceDto;
import com.andresantiago.vendorsservice.dto.VendorDto;
import com.andresantiago.vendorsservice.dto.VendorsStatisticsDto;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.exception.BusinessException;
import com.andresantiago.vendorsservice.repository.VendorDatabaseInMemory;
import com.andresantiago.vendorsservice.stubs.CreateVendorRequestStub;
import com.andresantiago.vendorsservice.stubs.LocationStub;
import com.andresantiago.vendorsservice.stubs.VendorStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VendorServiceTest {

    @Mock
    private VendorDatabaseInMemory vendorDatabaseInMemory;

    @InjectMocks
    private VendorService vendorService;

    @Test
    void shouldCreateAVendorWithSuccess() {
        CreateVendorRequest validCreateVendorRequest = CreateVendorRequestStub.createAValidRequest();

        vendorService.createVendor(validCreateVendorRequest);

        verify(vendorDatabaseInMemory, times(1)).createVendor(any(VendorEntity.class));
    }

    @Test
    void shouldReturnAVendorWithSuccess_givenAValidTaxId() {
        String vendorTaxId = "1";
        VendorEntity vendorStub = VendorStub.createEntity();

        when(vendorDatabaseInMemory.getVendorByTaxId(eq(vendorTaxId)))
                .thenReturn(vendorStub);

        VendorEntity vendor = vendorService.findVendorByTaxId(vendorTaxId);

        verify(vendorDatabaseInMemory, times(1)).getVendorByTaxId(eq(vendorTaxId));
        assertNotNull(vendor);
        assertEquals("1", vendor.getTaxId());
    }

    @Test
    void whenFindingVendorByTaxIdDoesNotExists_shouldThrowABusinessException() {
        String vendorTaxId = "1";
        BusinessException businessException = new BusinessException("Vendor not found");

        when(vendorDatabaseInMemory.getVendorByTaxId(eq(vendorTaxId)))
                .thenThrow(businessException);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> vendorService.findVendorByTaxId(vendorTaxId));

        verify(vendorDatabaseInMemory, times(1)).getVendorByTaxId(eq(vendorTaxId));
        assertEquals("Vendor not found", exception.getMessage());
    }

    @Test
    void shouldFilterVendorsByService() {
        VendorEntity vendorAirConditioning = VendorStub.createEntity(ServiceCategoryEnum.AIR_CONDITIONING, true);
        VendorEntity vendorLandscapeMaintenance = VendorStub.createEntity(ServiceCategoryEnum.LANDSCAPING_MAINTENANCE, true);

        List<VendorEntity> vendors = List.of(vendorAirConditioning, vendorLandscapeMaintenance);
        ServiceCategoryEnum serviceCategoryFilter = ServiceCategoryEnum.AIR_CONDITIONING;

        List<VendorEntity> filteredVendors = vendorService.filterVendorsByService(vendors, serviceCategoryFilter);

        assertNotNull(filteredVendors);
        assertFalse(filteredVendors.isEmpty());
        assertEquals(1, filteredVendors.size());
        assertEquals(ServiceCategoryEnum.AIR_CONDITIONING, filteredVendors.get(0).getServices().get(0).getServiceCategory());
    }

    @Test
    void shouldFilterVendorsByLocation() {
        VendorEntity vendorFromCapivari = VendorStub.createEntity("Capivari do Sul", "RS");
        VendorEntity vendorFromFayette = VendorStub.createEntity("Fayette", "TX");

        List<VendorEntity> vendors = List.of(vendorFromCapivari, vendorFromFayette);
        LocationRequest locationRequest = LocationStub.createRequest("Capivari do Sul", "RS");

        when(vendorDatabaseInMemory.getVendors())
                .thenReturn(vendors);

        List<VendorEntity> filteredVendors = vendorService.findVendorsByLocation(locationRequest);

        assertNotNull(filteredVendors);
        assertFalse(filteredVendors.isEmpty());
        assertEquals(1, filteredVendors.size());
        assertEquals("Capivari do Sul", filteredVendors.get(0).getLocation().getName());
        assertEquals("RS", filteredVendors.get(0).getLocation().getState());
    }

    @Test
    void shouldReturnAllVendorsFromDatabase() {
        when(vendorDatabaseInMemory.getVendors())
                .thenReturn(List.of(VendorStub.createEntity()));

        List<VendorEntity> vendors = vendorService.findAllVendors();

        assertNotNull(vendors);
        assertFalse(vendors.isEmpty());
        assertEquals(1, vendors.size());
    }

    @Test
    void shouldUpdateAVendorComplianceWithSuccess() {
        String vendorTaxId = "1";
        ServiceCategoryEnum vendorServiceCategory = ServiceCategoryEnum.AIR_CONDITIONING;
        boolean isCompliantUpdate = true;

        VendorEntity vendor = VendorStub.createEntity(ServiceCategoryEnum.AIR_CONDITIONING, false);

        when(vendorDatabaseInMemory.getVendorByTaxId(eq(vendorTaxId)))
                .thenReturn(vendor);

        vendorService.updateCompliance(vendorTaxId, vendorServiceCategory, isCompliantUpdate);

        assertTrue(vendor.getServices().get(0).isCompliant());
    }

    @Test
    void shouldThrowBusinessException_AtUpdateAVendorCompliance_whenVendorDoesNotProvideTheService() {
        String vendorTaxId = "1";
        ServiceCategoryEnum vendorServiceCategory = ServiceCategoryEnum.LANDSCAPING_MAINTENANCE;
        boolean isCompliantUpdate = true;

        VendorEntity vendor = VendorStub.createEntity(ServiceCategoryEnum.AIR_CONDITIONING, false);

        when(vendorDatabaseInMemory.getVendorByTaxId(eq(vendorTaxId)))
                .thenReturn(vendor);

        BusinessException businessException = assertThrows(BusinessException.class,
                () -> vendorService.updateCompliance(vendorTaxId, vendorServiceCategory, isCompliantUpdate));

        assertEquals("Vendor does not offer this service.", businessException.getMessage());
    }

    @Test
    void shouldIncludeAServiceToAVendorWithSuccess() {
        String vendorTaxId = "1";
        ServiceCategoryEnum serviceToBeIncluded = ServiceCategoryEnum.LANDSCAPING_MAINTENANCE;
        boolean isCompliant = true;

        VendorEntity vendor = VendorStub.createEntity(ServiceCategoryEnum.AIR_CONDITIONING, false);

        when(vendorDatabaseInMemory.getVendorByTaxId(eq(vendorTaxId)))
                .thenReturn(vendor);

        vendorService.includeService(vendorTaxId, serviceToBeIncluded, isCompliant);

        List<ServiceDto> updatedVendorServices = vendor.getServices();
        assertEquals(2, updatedVendorServices.size());
        assertEquals(ServiceCategoryEnum.LANDSCAPING_MAINTENANCE, updatedVendorServices.get(1).getServiceCategory());
        assertTrue(updatedVendorServices.get(1).isCompliant());
    }

    @Test
    void shouldThrowBusinessException_atIncludingAServiceToAVendor_whenItAlreadyProvideTheService() {
        String vendorTaxId = "1";
        ServiceCategoryEnum serviceToBeIncluded = ServiceCategoryEnum.AIR_CONDITIONING;
        boolean isCompliant = true;

        VendorEntity vendor = VendorStub.createEntity(ServiceCategoryEnum.AIR_CONDITIONING, false);

        when(vendorDatabaseInMemory.getVendorByTaxId(eq(vendorTaxId)))
                .thenReturn(vendor);


        BusinessException businessException = assertThrows(BusinessException.class,
                () -> vendorService.includeService(vendorTaxId, serviceToBeIncluded, isCompliant));

        List<ServiceDto> updatedVendorServices = vendor.getServices();
        assertEquals(1, updatedVendorServices.size());
        assertEquals("Vendor already offer this service.", businessException.getMessage());
    }

    @Test
    void givenTheJob_shouldReturnPotentialVendorsSortedByCompliance() {
        LocationRequest locationRequest = LocationStub.createRequest("Capivari do Sul", "RS");
        ServiceCategoryEnum serviceCategory = ServiceCategoryEnum.AIR_CONDITIONING;

        List<VendorEntity> vendorsListStub = VendorStub.createVendorsForTesting();

        when(vendorDatabaseInMemory.getVendors())
                .thenReturn(vendorsListStub);

        List<VendorDto> potentialVendorsByJob = vendorService.findPotentialVendorsByJob(locationRequest, serviceCategory);

        assertNotNull(potentialVendorsByJob);
        assertFalse(potentialVendorsByJob.isEmpty());
        assertEquals(3, potentialVendorsByJob.size());

        assertEquals("Vendor 3", potentialVendorsByJob.get(0).getName());
        assertTrue(potentialVendorsByJob.get(0).isCompliant());

        assertEquals("Vendor 5", potentialVendorsByJob.get(1).getName());
        assertTrue(potentialVendorsByJob.get(1).isCompliant());

        assertEquals("Vendor 2", potentialVendorsByJob.get(2).getName());
        assertFalse(potentialVendorsByJob.get(2).isCompliant());

    }

    @Test
    void shouldReturnVendorsStatistics_givenTheJob() {
        LocationRequest locationRequest = LocationStub.createRequest("Capivari do Sul", "RS");
        ServiceCategoryEnum serviceCategory = ServiceCategoryEnum.AIR_CONDITIONING;

        List<VendorEntity> vendorsForTesting = VendorStub.createVendorsForTesting();

        when(vendorDatabaseInMemory.getVendors())
                .thenReturn(vendorsForTesting);

        VendorsStatisticsDto statistics = vendorService.getVendorsStatisticsByJob(locationRequest, serviceCategory);

        assertNotNull(statistics);
        assertEquals(ServiceCategoryEnum.AIR_CONDITIONING, statistics.getServiceCategory());
        assertEquals("Capivari do Sul", statistics.getLocation().getName());
        assertEquals("RS", statistics.getLocation().getState());
        assertEquals(3, statistics.getTotalVendors());
        assertEquals(2, statistics.getTotalCompliant());
        assertEquals(1, statistics.getTotalNotCompliant());
    }
}