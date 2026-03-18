package com.cognizant.healthcaregov.service;

import com.cognizant.healthcaregov.dao.AuditLogRepository;
import com.cognizant.healthcaregov.dao.ComplianceRecordRepository;
import com.cognizant.healthcaregov.dto.ComplianceRequestDTO;
import com.cognizant.healthcaregov.dto.ComplianceResponseDTO;
import com.cognizant.healthcaregov.entity.AuditLog;
import com.cognizant.healthcaregov.entity.ComplianceRecord;
import com.cognizant.healthcaregov.entity.User;
import com.cognizant.healthcaregov.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComplianceServiceImpl implements ComplianceService {

    @Autowired
    private ComplianceRecordRepository complianceRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    @Transactional
    public ComplianceResponseDTO createComplianceRecord(ComplianceRequestDTO requestDTO, Integer currentUserId) {
        // 1. Create Compliance Record
        ComplianceRecord record = new ComplianceRecord();
        record.setEntityId(requestDTO.getEntityId());
        record.setType(requestDTO.getType());
        record.setResult(requestDTO.getResult());
        record.setNotes(requestDTO.getNotes());

        ComplianceRecord savedRecord = complianceRepository.save(record);

        // 2. Log Action
        logAuditAction(currentUserId, "CREATE", "ComplianceRecord-" + savedRecord.getComplianceID());

        return mapToDTO(savedRecord);
    }

    @Override
    @Transactional
    public ComplianceResponseDTO updateComplianceRecord(Integer id, ComplianceRequestDTO requestDTO, Integer currentUserId) {
        // 1. Fetch and Update Compliance Record
        ComplianceRecord existingRecord = complianceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance Record not found with ID: " + id));

        existingRecord.setResult(requestDTO.getResult());
        existingRecord.setNotes(requestDTO.getNotes());

        ComplianceRecord updatedRecord = complianceRepository.save(existingRecord);

        // 2. Log Action
        logAuditAction(currentUserId, "UPDATE", "ComplianceRecord-" + updatedRecord.getComplianceID());

        return mapToDTO(updatedRecord);
    }

    private void logAuditAction(Integer userId, String action, String resource) {
        AuditLog log = new AuditLog();

        // Mocking user proxy for logging since User is managed by another team's module
        User mockUser = new User();
        mockUser.setUserID(userId);

        log.setUser(mockUser);
        log.setAction(action);
        log.setResource(resource);
        auditLogRepository.save(log);
    }

    private ComplianceResponseDTO mapToDTO(ComplianceRecord record) {
        ComplianceResponseDTO dto = new ComplianceResponseDTO();
        dto.setComplianceID(record.getComplianceID());
        dto.setEntityId(record.getEntityId());
        dto.setType(record.getType());
        dto.setResult(record.getResult());
        dto.setDate(record.getDate());
        dto.setNotes(record.getNotes());
        return dto;
    }
}