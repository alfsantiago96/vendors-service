package com.andresantiago.vendorsservice.api.rest.v1;

import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.service.JobService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JobApiContextTest {

    private static String BASE_URI = "/v1/jobs";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JobService jobService;

    @Test
    @Order(10)
    void givenAValidContract_shouldNotHireAJob_whenUserIsNotAuthorized() throws Exception {
        String companyTaxId = "999";
        String serviceCategory = ServiceCategoryEnum.AIR_CONDITIONING.name();
        String vendorTaxId = "7";
        String locationName = "Fayette";
        String locationState = "TX";

        mockMvc.perform(post(BASE_URI)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("andre", "123"))
                        .param("companyTaxId", companyTaxId)
                        .param("serviceCategory", serviceCategory)
                        .param("vendorTaxId", vendorTaxId)
                        .param("locationName", locationName)
                        .param("locationState", locationState))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @Order(11)
    void givenAInvalidContract_shouldNotHireAJob() throws Exception {
        String companyTaxId = "999";
        String serviceCategory = ServiceCategoryEnum.AIR_CONDITIONING.name();
        String vendorTaxId = "7";
        String locationName = "Capivari do Sul";
        String locationState = null;

        mockMvc.perform(post(BASE_URI)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("job", "123"))
                        .param("companyTaxId", companyTaxId)
                        .param("serviceCategory", serviceCategory)
                        .param("vendorTaxId", vendorTaxId)
                        .param("locationName", locationName)
                        .param("locationState", locationState))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @Order(12)
    void givenAValidContract_shouldHireAJob_whenUserIsAuthorized() throws Exception {
        String companyTaxId = "999";
        String serviceCategory = ServiceCategoryEnum.AIR_CONDITIONING.name();
        String vendorTaxId = "1";
        String locationName = "Fayette";
        String locationState = "TX";

        mockMvc.perform(post(BASE_URI)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("job", "123"))
                        .param("companyTaxId", companyTaxId)
                        .param("serviceCategory", serviceCategory)
                        .param("vendorTaxId", vendorTaxId)
                        .param("locationName", locationName)
                        .param("locationState", locationState))
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    @Order(13)
    void givenAValidCompanyTaxId_shouldReturnAListOfJobs() throws Exception {
        String companyTaxId = "999";
        String output = new String(Files.readAllBytes(Paths.get("src/test/resources/json/response/GetJobByCompanyTaxIdContextResponse.json")));

        mockMvc.perform(get(BASE_URI + "/company/" + companyTaxId)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("andre", "123")))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(output));
    }

    @Test
    @Order(14)
    void givenAValidVendorTaxId_shouldReturnAListOfJobs() throws Exception {
        String vendorTaxId = "1";
        String output = new String(Files.readAllBytes(Paths.get("src/test/resources/json/response/GetJobByCompanyTaxIdContextResponse.json")));

        mockMvc.perform(get(BASE_URI + "/vendor/" + vendorTaxId)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("andre", "123")))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(output));
    }
}