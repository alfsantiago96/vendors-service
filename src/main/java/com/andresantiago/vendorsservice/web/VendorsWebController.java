package com.andresantiago.vendorsservice.web;

import com.andresantiago.vendorsservice.web.request.GetVendorsStatisticsByJobRequest;
import com.andresantiago.vendorsservice.api.rest.v1.request.LocationRequest;
import com.andresantiago.vendorsservice.dto.VendorsStatisticsDto;
import com.andresantiago.vendorsservice.enums.ServiceCategoryEnum;
import com.andresantiago.vendorsservice.service.VendorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class VendorsWebController {

    private final VendorService vendorService;
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/vendors")
    public String getVendorsStatisticsByJob(@ModelAttribute GetVendorsStatisticsByJobRequest request, Model model) {
        log.info("Model Request: {}", request);
        LocationRequest locationRequest = LocationRequest.builder()
                .name(request.getLocationName())
                .state(request.getLocationState())
                .build();
        ServiceCategoryEnum serviceCategoryEnum = ServiceCategoryEnum.valueOf(request.getServiceCategory());
        VendorsStatisticsDto vendorStatistics = vendorService.getVendorsStatisticsByJob(locationRequest, serviceCategoryEnum);
        model.addAttribute("serviceCategory", vendorStatistics.getServiceCategory());
        model.addAttribute("locationName", vendorStatistics.getLocation().getName());
        model.addAttribute("locationState", vendorStatistics.getLocation().getState());
        model.addAttribute("totalVendors", vendorStatistics.getTotalVendors());
        model.addAttribute("totalCompliant", vendorStatistics.getTotalCompliant());
        model.addAttribute("totalNotCompliant", vendorStatistics.getTotalNotCompliant());
        log.info("Vendor got with success.");
        return "result";
    }
}