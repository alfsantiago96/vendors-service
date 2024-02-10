package com.andresantiago.vendorsservice.api.v1;

import com.andresantiago.vendorsservice.api.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.dto.JobDto;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

//    @Test
    void shouldCreateAVendor_givenAValidContract() throws Exception {
        //TODO
        String locationName = "Capivari do Sul";
        String locationState = "RS";
        String service = ServiceCategoryEnum.AIR_CONDITIONING.name();
        LocationRequest locationRequest = LocationRequest.builder().name(locationName).state(locationState).build();

        VendorsStatisticsDto vendorsStatisticsDto = VendorStub.createVendorsStatisticsDto();

        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/json/response/GetVendorsStatisticsByJobResponse.json")));

        when(vendorService.getVendorsStatisticsByJob(locationRequest, ServiceCategoryEnum.AIR_CONDITIONING))
                .thenReturn(vendorsStatisticsDto);

        mockMvc.perform(get(BASE_URI + "/jobs/statistics")
                        .header(HttpHeaders.AUTHORIZATION, "BASIC")
                        .param("locationName", locationName)
                        .param("locationState", locationState)
                        .param("service", service))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(input));
    }
}