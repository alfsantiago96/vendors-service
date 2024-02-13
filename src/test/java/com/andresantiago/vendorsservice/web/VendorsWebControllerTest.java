package com.andresantiago.vendorsservice.web;

import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.service.VendorService;
import com.andresantiago.vendorsservice.web.request.GetVendorsStatisticsByJobRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VendorsWebControllerTest {

    private static String BASE_URI = "/vendors";

    @Autowired
    private VendorService vendorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetVendorsStatistics_givenAValidContract() throws Exception {
        GetVendorsStatisticsByJobRequest request = GetVendorsStatisticsByJobRequest.
                builder()
                .locationState("Capivari do Sul")
                .locationName("RS")
                .serviceCategory(ServiceCategoryEnum.AIR_CONDITIONING.name())
                .build();

        mockMvc.perform(post(BASE_URI)
                        .flashAttr("request", request))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    void shouldGetTheIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
}