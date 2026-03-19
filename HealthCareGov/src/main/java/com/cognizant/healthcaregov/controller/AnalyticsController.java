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
    public ResponseEntity<Map<String, Object>> getHospitalAnalytics() {
        Map<String, Object> analytics = analyticsService.getHospitalAnalytics();
        return ResponseEntity.ok(analytics);
    }

    @GetMapping("/reports/hospital-capacity")
    public ResponseEntity<Map<String, Object>> getHospitalCapacityReport() {
        Map<String, Object> report = analyticsService.getHospitalCapacityReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/resource-availability")
    public ResponseEntity<Map<String, Object>> getResourceAvailabilityReport() {
        Map<String, Object> report = analyticsService.getResourceAvailabilityReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/resource-distribution")
    public ResponseEntity<Map<String, Object>> getResourceDistributionReport() {
        Map<String, Object> report = analyticsService.getResourceDistributionReport();
        return ResponseEntity.ok(report);
    }
}