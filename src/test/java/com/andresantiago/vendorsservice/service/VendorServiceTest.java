package com.andresantiago.vendorsservice.service;

import com.andresantiago.vendorsservice.api.v1.request.CreateVendorRequest;
import com.andresantiago.vendorsservice.entity.VendorEntity;
import com.andresantiago.vendorsservice.exception.BusinessException;
import com.andresantiago.vendorsservice.repository.VendorDatabaseInMemory;
import com.andresantiago.vendorsservice.stubs.CreateVendorRequestStub;
import com.andresantiago.vendorsservice.stubs.VendorStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void shouldReturnAllVendors() {
        List<VendorEntity> allVendors = List.of(VendorStub.createEntity());

        when(vendorDatabaseInMemory.getVendors())
                .thenReturn(allVendors);

        List<VendorEntity> vendors = vendorService.findAllVendors();

        verify(vendorDatabaseInMemory, times(1)).getVendors();
        assertNotNull(vendors);
        VendorEntity vendor = vendors.get(0);
        assertEquals("1", vendor.getTaxId());
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
}