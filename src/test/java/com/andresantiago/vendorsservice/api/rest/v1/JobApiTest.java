package com.andresantiago.vendorsservice.api.rest.v1;

import com.andresantiago.vendorsservice.api.rest.v1.JobApi;
import com.andresantiago.vendorsservice.dto.JobDto;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.service.JobService;
import com.andresantiago.vendorsservice.stubs.JobStub;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class JobApiTest {

    private static String BASE_URI = "/v1/jobs";
    @Mock
    private JobService jobService;

    private static MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        JobApi api = new JobApi(jobService);

        mockMvc = MockMvcBuilders.standaloneSetup(api)
                .build();
    }

    @Test
    void shouldHireAJob_givenAValidContract() throws Exception {
        String companyTaxId = "999";
        String serviceCategory = ServiceCategoryEnum.AIR_CONDITIONING.name();
        String vendorTaxId = "7";
        String locationName = "Capivari do Sul";
        String locationState = "RS";

        mockMvc.perform(post(BASE_URI)
                        .header(HttpHeaders.AUTHORIZATION, "BASIC")
                        .param("companyTaxId", companyTaxId)
                        .param("serviceCategory", serviceCategory)
                        .param("vendorTaxId", vendorTaxId)
                        .param("locationName", locationName)
                        .param("locationState", locationState))
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    void shouldReturnAListOfJobs_givenAValidCompanyTaxId() throws Exception {
        String companyTaxId = "999";
        List<JobDto> jobDtoList = List.of(JobStub.createDto());

        String output = new String(Files.readAllBytes(Paths.get("src/test/resources/json/response/GetJobByCompanyTaxId.json")));


        when(jobService.findJobsByCompany(eq(companyTaxId)))
                        .thenReturn(jobDtoList);

        mockMvc.perform(get(BASE_URI + "/company/" + companyTaxId)
                        .header(HttpHeaders.AUTHORIZATION, "BASIC"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(output));
    }

    @Test
    void shouldReturnAListOfJobs_givenAValidVendorTaxId() throws Exception {
        String vendorTaxId = "1";
        List<JobDto> jobDtoList = List.of(JobStub.createDto());

        String output = new String(Files.readAllBytes(Paths.get("src/test/resources/json/response/GetJobByCompanyTaxId.json")));

        when(jobService.findJobsByVendor(eq(vendorTaxId)))
                .thenReturn(jobDtoList);

        mockMvc.perform(get(BASE_URI + "/vendor/" + vendorTaxId)
                        .header(HttpHeaders.AUTHORIZATION, "BASIC"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(output));
    }
}