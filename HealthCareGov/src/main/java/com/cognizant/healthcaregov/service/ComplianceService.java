package com.cognizant.healthcaregov.service;

import com.cognizant.healthcaregov.dto.ComplianceRequestDTO;
import com.cognizant.healthcaregov.dto.ComplianceResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ComplianceService {
    ComplianceResponseDTO createComplianceRecord(ComplianceRequestDTO requestDTO, Integer currentUserId);
    ComplianceResponseDTO updateComplianceRecord(Integer id, ComplianceRequestDTO requestDTO, Integer currentUserId);

    // COMP-002: Search & Filter method
    List<ComplianceResponseDTO> searchRecords(String type, String result, Integer entityId, LocalDate startDate, LocalDate endDate);
}