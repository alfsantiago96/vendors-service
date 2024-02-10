package com.andresantiago.vendorsservice.api.v1;

import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.repository.VendorDatabaseInMemory;
import com.andresantiago.vendorsservice.service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class DevUtilsApiTest {
    private static String BASE_URI = "/database";
    @Mock
    private VendorDatabaseInMemory vendorDatabaseInMemory;

    private static MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        DevUtilsApi api = new DevUtilsApi(vendorDatabaseInMemory);

        mockMvc = MockMvcBuilders.standaloneSetup(api)
                .build();
    }

    @Test
    void shouldPopulateVendorsDatabaseWithSuccess() throws Exception {



        mockMvc.perform(post(BASE_URI)
                        .header(HttpHeaders.AUTHORIZATION, "BASIC"))
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    void shouldDropVendorsDatabaseWithSuccess() throws Exception {
        mockMvc.perform(delete(BASE_URI)
                        .header(HttpHeaders.AUTHORIZATION, "BASIC"))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
}