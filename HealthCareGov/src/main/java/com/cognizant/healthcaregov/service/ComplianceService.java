package com.cognizant.healthcaregov.service;

import com.cognizant.healthcaregov.dto.ComplianceRequestDTO;
import com.cognizant.healthcaregov.dto.ComplianceResponseDTO;

public interface ComplianceService {
    ComplianceResponseDTO createComplianceRecord(ComplianceRequestDTO requestDTO, Integer currentUserId);
    ComplianceResponseDTO updateComplianceRecord(Integer id, ComplianceRequestDTO requestDTO, Integer currentUserId);
}