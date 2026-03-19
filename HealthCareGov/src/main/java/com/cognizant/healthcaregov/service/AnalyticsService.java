package com.cognizant.healthcaregov.service;

import com.cognizant.healthcaregov.dao.HospitalRepository;
import com.cognizant.healthcaregov.dao.ResourceRepository;
import com.cognizant.healthcaregov.entity.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AnalyticsService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    public Map<String, Object> getHospitalAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("totalHospitals", hospitalRepository.count());
        analytics.put("totalBeds", resourceRepository.sumQuantityByType("Beds"));
        analytics.put("totalEquipment", resourceRepository.sumQuantityByType("Equipment"));
        analytics.put("totalStaff", resourceRepository.sumQuantityByType("Staff"));
        return analytics;
    }

    public Map<String, Object> getHospitalCapacityReport() {
        // This could be more detailed, but for now, return total capacity
        Map<String, Object> report = new HashMap<>();
        report.put("totalCapacity", hospitalRepository.findAll().stream()
                .mapToInt(Hospital::getCapacity)
                .sum());
        return report;
    }

    public Map<String, Object> getResourceAvailabilityReport() {
        Map<String, Object> report = new HashMap<>();
        report.put("bedsAvailable", resourceRepository.sumQuantityByType("Beds"));
        report.put("equipmentAvailable", resourceRepository.sumQuantityByType("Equipment"));
        report.put("staffAvailable", resourceRepository.sumQuantityByType("Staff"));
        return report;
    }

    public Map<String, Object> getResourceDistributionReport() {
        // Group by hospital or something, but for simplicity, return totals
        return getResourceAvailabilityReport();
    }
}