package com.cognizant.healthcaregov.controller;

import com.cognizant.healthcaregov.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {


    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/hospitals")
    public Map<String,Object> getHospitalAnalytics() {
        return analyticsService.getHospitalAnalytics();
    }

    @GetMapping("/reports/hospital-capacity")
    public Map<String,Object> getHospitalCapacityReport() {
        return analyticsService.getHospitalCapacityReport();

    }

    @GetMapping("/reports/resource-availability")
    public Map<String,Object> getResourceAvailabilityReport() {
        return analyticsService.getResourceAvailabilityReport();
    }

    @GetMapping("/reports/resource-distribution")
    public Map<String,Object> getResourceDistributionReport() {
        return analyticsService.getResourceDistributionReport();
    }
}