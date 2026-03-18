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

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ComplianceServiceImpl implements ComplianceService {

    @Autowired
    private ComplianceRecordRepository complianceRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public ComplianceResponseDTO createComplianceRecord(ComplianceRequestDTO requestDTO, Integer currentUserId) {
        ComplianceRecord record = new ComplianceRecord();
        record.setEntityId(requestDTO.getEntityId());
        record.setType(requestDTO.getType());
        record.setResult(requestDTO.getResult());
        record.setNotes(requestDTO.getNotes());
        record.setDate(LocalDate.now());

        ComplianceRecord savedRecord = complianceRepository.save(record);

        logAuditAction("CREATE", "ComplianceRecord-" + savedRecord.getComplianceID(), currentUserId);

        return mapToDTO(savedRecord);
    }

    @Override
    public ComplianceResponseDTO updateComplianceRecord(Integer id, ComplianceRequestDTO requestDTO, Integer currentUserId) {
        ComplianceRecord existingRecord = complianceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance Record not found with ID: " + id));

        existingRecord.setResult(requestDTO.getResult());
        existingRecord.setNotes(requestDTO.getNotes());

        ComplianceRecord updatedRecord = complianceRepository.save(existingRecord);

        logAuditAction("UPDATE", "ComplianceRecord-" + updatedRecord.getComplianceID(), currentUserId);

        return mapToDTO(updatedRecord);
    }

    // COMP-002: Implementation
    @Override
    public List<ComplianceResponseDTO> searchRecords(String type, String result, Integer entityId, LocalDate startDate, LocalDate endDate) {
        List<ComplianceRecord> records = complianceRepository.searchComplianceRecords(type, result, entityId, startDate, endDate);
        return records.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private void logAuditAction(String action, String resource, Integer userId) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setResource(resource);
        auditLog.setTimestamp(Instant.now()); // Using Instant to match your Entity

        User mockUser = new User();
        mockUser.setUserID(userId); // Using Capital D
        auditLog.setUser(mockUser);

        auditLogRepository.save(auditLog);
    }

    private ComplianceResponseDTO mapToDTO(ComplianceRecord record) {
        ComplianceResponseDTO dto = new ComplianceResponseDTO();
        dto.setComplianceID(record.getComplianceID()); // Capital D's
        dto.setEntityId(record.getEntityId());
        dto.setType(record.getType());
        dto.setDate(record.getDate());
        dto.setResult(record.getResult());
        dto.setNotes(record.getNotes());
        return dto;
    }
}