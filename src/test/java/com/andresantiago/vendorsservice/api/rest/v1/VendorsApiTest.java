package com.andresantiago.vendorsservice.api.rest.v1;

import com.andresantiago.vendorsservice.api.rest.v1.VendorsApi;
import com.andresantiago.vendorsservice.api.rest.v1.request.CreateVendorRequest;
import com.andresantiago.vendorsservice.api.rest.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.dto.VendorDto;
import com.andresantiago.vendorsservice.dto.VendorsStatisticsDto;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.service.VendorService;
import com.andresantiago.vendorsservice.stubs.VendorStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class VendorsApiTest {

    private static String BASE_URI = "/v1/vendors";

    @Mock
    private VendorService vendorService;

    private static MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        VendorsApi api = new VendorsApi(vendorService);

        mockMvc = MockMvcBuilders.standaloneSetup(api)
                .build();
    }

    @Test
    void shouldCreateAVendor_givenAValidContract() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/json/request/CreateVendorRequest.json")));

        mockMvc.perform(post(BASE_URI)
                        .header(HttpHeaders.AUTHORIZATION, "BASIC")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(HttpStatus.CREATED.value()));

        verify(vendorService, times(1)).createVendor(any(CreateVendorRequest.class));
    }

    @Test
    void shouldNotCreateAVendor_withoutProvidingTheServiceContract() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/json/request/CreateVendorInvalidRequest.json")));

        mockMvc.perform(post(BASE_URI)
                        .header(HttpHeaders.AUTHORIZATION, "BASIC")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void shouldNotCreateAVendor_withoutProvidingAValidLocation() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/json/request/CreateVendorInvalidLocationRequest.json")));

        mockMvc.perform(post(BASE_URI)
                        .header(HttpHeaders.AUTHORIZATION, "BASIC")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void shouldReturnAListOfPotentialVendors_givenTheJobParameters() throws Exception {
        String locationName = "Capivari do Sul";
        String locationState = "RS";
        String service = ServiceCategoryEnum.AIR_CONDITIONING.name();
        LocationRequest locationRequest = LocationRequest.builder().name(locationName).state(locationState).build();

        List<VendorDto> vendorDtoList = List.of(VendorStub.createDto());

        String output = new String(Files.readAllBytes(Paths.get("src/test/resources/json/response/GetPotentialVendorsByJobResponse.json")));

        when(vendorService.findPotentialVendorsByJob(locationRequest, ServiceCategoryEnum.AIR_CONDITIONING))
                .thenReturn(vendorDtoList);

        mockMvc.perform(get(BASE_URI + "/jobs")
                        .header(HttpHeaders.AUTHORIZATION, "BASIC")
                        .param("locationName", locationName)
                        .param("locationState", locationState)
                        .param("service", service))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(output));
    }

    @Test
    void shouldReturnAVendorStatisticsReport_givenTheJobParameters() throws Exception {
        String locationName = "Capivari do Sul";
        String locationState = "RS";
        String service = ServiceCategoryEnum.AIR_CONDITIONING.name();
        LocationRequest locationRequest = LocationRequest.builder().name(locationName).state(locationState).build();

        VendorsStatisticsDto vendorsStatisticsDto = VendorStub.createVendorsStatisticsDto();

        String output = new String(Files.readAllBytes(Paths.get("src/test/resources/json/response/GetVendorsStatisticsByJobResponse.json")));

        when(vendorService.getVendorsStatisticsByJob(locationRequest, ServiceCategoryEnum.AIR_CONDITIONING))
                .thenReturn(vendorsStatisticsDto);

        mockMvc.perform(get(BASE_URI + "/jobs/statistics")
                        .header(HttpHeaders.AUTHORIZATION, "BASIC")
                        .param("locationName", locationName)
                        .param("locationState", locationState)
                        .param("service", service))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(output));
    }

    @Test
    void shouldIncludeAJobToAVendor_withSuccess() throws Exception {
        String vendorTaxId = "1";
        ServiceCategoryEnum serviceCategoryEnum = ServiceCategoryEnum.AIR_CONDITIONING;
        mockMvc.perform(put(BASE_URI + "/" + vendorTaxId + "/jobs")
                        .header(HttpHeaders.AUTHORIZATION, "BASIC")
                        .param("serviceCategoryEnum", serviceCategoryEnum.name())
                        .param("isCompliant", "true"))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    void shouldUpdateVendorCompliance_withSuccess() throws Exception {
        String vendorTaxId = "1";
        ServiceCategoryEnum serviceCategoryEnum = ServiceCategoryEnum.AIR_CONDITIONING;
        mockMvc.perform(put(BASE_URI + "/" + vendorTaxId + "/compliance")
                        .header(HttpHeaders.AUTHORIZATION, "BASIC")
                        .param("serviceCategoryEnum", serviceCategoryEnum.name())
                        .param("isCompliant", "true"))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    void shouldUReturnVendorByTaxId_withSuccess() throws Exception {
        String vendorTaxId = "1";
        ServiceCategoryEnum serviceCategoryEnum = ServiceCategoryEnum.AIR_CONDITIONING;
        mockMvc.perform(get(BASE_URI + "/" + vendorTaxId)
                        .header(HttpHeaders.AUTHORIZATION, "BASIC"))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
}