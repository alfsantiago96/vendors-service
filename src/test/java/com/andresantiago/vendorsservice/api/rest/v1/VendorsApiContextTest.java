package com.andresantiago.vendorsservice.api.rest.v1;

import com.andresantiago.vendorsservice.api.rest.v1.request.CreateVendorRequest;
import com.andresantiago.vendorsservice.api.rest.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.dto.ServiceDto;
import com.andresantiago.vendorsservice.dto.VendorsStatisticsDto;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.repository.VendorDatabaseInMemory;
import com.andresantiago.vendorsservice.service.VendorService;
import com.andresantiago.vendorsservice.stubs.VendorStub;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VendorsApiContextTest {

    private static String BASE_URI = "/v1/vendors";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VendorDatabaseInMemory vendorDatabaseInMemory;

    @Autowired
    private VendorService vendorService;

    @Test
    @Order(1)
    void givenAValidContract_shouldNotCreateAVendor_whenUserIsNotAuthorized() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/json/request/CreateVendorRequest.json")));

        mockMvc.perform(post(BASE_URI)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("andre", "123"))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @Order(2)
    void givenAInvalidContract_shouldNotCreateAVendor() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/json/request/CreateVendorInvalidRequest.json")));

        mockMvc.perform(post(BASE_URI)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("vendor", "123"))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @Order(3)
    void shouldNotCreateAVendor_withoutProvidingTheServiceContract() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/json/request/CreateVendorInvalidRequest.json")));

        mockMvc.perform(post(BASE_URI)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("vendor", "123"))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @Order(4)
    void shouldNotCreateAVendor_withoutProvidingAValidLocation() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/json/request/CreateVendorInvalidLocationRequest.json")));

        mockMvc.perform(post(BASE_URI)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("vendor", "123"))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @Order(4)
    void shouldNotCreateAVendor_withoutProvidingAValidService() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/json/request/CreateVendorInvalidServiceRequest.json")));

        mockMvc.perform(post(BASE_URI)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("vendor", "123"))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @Order(5)
    void givenAValidContract_shouldCreateAVendor_whenUserIsAuthorized() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/json/request/CreateVendorRequest.json")));

        mockMvc.perform(post(BASE_URI)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("vendor", "123"))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    @Order(6)
    void shouldIncludeAJobToAVendor_withSuccess() throws Exception {
        String vendorTaxId = "10";
        ServiceCategoryEnum serviceCategoryEnum = ServiceCategoryEnum.LANDSCAPING_MAINTENANCE;
        mockMvc.perform(put(BASE_URI + "/" + vendorTaxId + "/jobs")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("andre", "123"))
                        .param("serviceCategoryEnum", serviceCategoryEnum.name())
                        .param("isCompliant", "false"))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    @Order(7)
    void shouldUpdateVendorCompliance_withSuccess() throws Exception {
        String vendorTaxId = "10";
        ServiceCategoryEnum serviceCategoryEnum = ServiceCategoryEnum.SNOW_AND_ICE_REMOVAL;
        mockMvc.perform(put(BASE_URI + "/" + vendorTaxId + "/compliance")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("andre", "123"))
                        .param("serviceCategoryEnum", serviceCategoryEnum.name())
                        .param("isCompliant", "true"))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    @Order(8)
    void shouldUReturnVendorByTaxId_withSuccess() throws Exception {
        String vendorTaxId = "10";
        mockMvc.perform(get(BASE_URI + "/" + vendorTaxId)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("andre", "123")))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    @Order(9)
    void shouldReturnAListOfPotentialVendors_givenTheJobParameters() throws Exception {
        vendorDatabaseInMemory.eraseInMemoryData();

        List<String> vendorRequestList = getCreateVendorsRequestAsJson();

        for (String createVendorRequestAsJson : vendorRequestList) {
            mockMvc.perform(post(BASE_URI)
                            .with(SecurityMockMvcRequestPostProcessors.httpBasic("vendor", "123"))
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON)
                            .content(createVendorRequestAsJson))
                    .andExpect(status().is(HttpStatus.CREATED.value()));
        }

        String locationName = "Fayette";
        String locationState = "TX";
        String service = ServiceCategoryEnum.AIR_CONDITIONING.name();

        String output = new String(Files.readAllBytes(Paths.get("src/test/resources/json/response/GetPotentialVendorsByJobContextResponse.json")));

        mockMvc.perform(get(BASE_URI + "/jobs")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("andre", "123"))
                        .param("locationName", locationName)
                        .param("locationState", locationState)
                        .param("service", service))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(output));
    }

    @Test
    @Order(10)
    void shouldReturnAVendorStatisticsReport_givenTheJobParameters() throws Exception {
        String locationName = "Fayette";
        String locationState = "TX";
        String service = ServiceCategoryEnum.AIR_CONDITIONING.name();

        String output = new String(Files.readAllBytes(Paths.get("src/test/resources/json/response/GetVendorsStatisticsByJobContextResponse.json")));

        mockMvc.perform(get(BASE_URI + "/jobs/statistics")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("andre", "123"))
                        .header(HttpHeaders.AUTHORIZATION, "BASIC")
                        .param("locationName", locationName)
                        .param("locationState", locationState)
                        .param("service", service))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(output));
    }

    @Test
    @Order(11)
    void givenAValidContract_shouldNotCreateAVendor_whenUserItAlreadyExists() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/json/request/CreateVendorAlreadyExistsRequest.json")));

        mockMvc.perform(post(BASE_URI)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("vendor", "123"))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @Order(12)
    void shouldThrowBusinessException_whenVendorDoesNotExists() throws Exception {
        String vendorTaxId = "20";
        mockMvc.perform(get(BASE_URI + "/" + vendorTaxId)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("andre", "123")))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @Order(13)
    void givenAValidContract_shouldNotCreateAVendor_whenUserIsDoesNotExists() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/json/request/CreateVendorRequest.json")));

        mockMvc.perform(post(BASE_URI)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("jonas", "123"))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    @Order(14)
    void givenAValidContract_shouldNotCreateAVendor_whenUserPasswordIsWrong() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/json/request/CreateVendorRequest.json")));

        mockMvc.perform(post(BASE_URI)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("andre", "1233"))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    private static String getCreateVendorRequestAsJson(String vendorNumber, LocationRequest locationRequest, ServiceDto serviceDto) throws JsonProcessingException {
        List<ServiceDto> services = new ArrayList<>();
        services.add(serviceDto);

        CreateVendorRequest request = CreateVendorRequest.builder()
                .name("Vendor " + vendorNumber)
                .taxId(vendorNumber)
                .location(LocationRequest.builder()
                        .name(locationRequest.getName())
                        .state(locationRequest.getState())
                        .build())
                .services(services)
                .build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(request);
    }

    private static List<String> getCreateVendorsRequestAsJson() throws JsonProcessingException {
        final String vendor1 = getCreateVendorRequestAsJson("1",
                LocationRequest.builder()
                        .name("Fayette").state("TX").build(),
                ServiceDto.builder()
                        .serviceCategory(ServiceCategoryEnum.AIR_CONDITIONING).isCompliant(false).build());

        final String vendor2 = getCreateVendorRequestAsJson("2",
                LocationRequest.builder()
                        .name("Fayette").state("TX").build(),
                ServiceDto.builder()
                        .serviceCategory(ServiceCategoryEnum.AIR_CONDITIONING).isCompliant(false).build());

        final String vendor3 = getCreateVendorRequestAsJson("3",
                LocationRequest.builder()
                        .name("Fayette").state("TX").build(),
                ServiceDto.builder()
                        .serviceCategory(ServiceCategoryEnum.AIR_CONDITIONING).isCompliant(true).build());

        final String vendor4 = getCreateVendorRequestAsJson("4",
                LocationRequest.builder()
                        .name("Glades").state("FL").build(),
                ServiceDto.builder()
                        .serviceCategory(ServiceCategoryEnum.LANDSCAPING_MAINTENANCE).isCompliant(true).build());

        final String vendor5 = getCreateVendorRequestAsJson("5",
                LocationRequest.builder()
                        .name("Glades").state("FL").build(),
                ServiceDto.builder()
                        .serviceCategory(ServiceCategoryEnum.AIR_CONDITIONING).isCompliant(false).build());

        final String vendor6 = getCreateVendorRequestAsJson("6",
                LocationRequest.builder()
                        .name("Glades").state("FL").build(),
                ServiceDto.builder()
                        .serviceCategory(ServiceCategoryEnum.AIR_CONDITIONING).isCompliant(true).build());

        return List.of(vendor1, vendor2, vendor3, vendor4, vendor5, vendor6);
    }
}