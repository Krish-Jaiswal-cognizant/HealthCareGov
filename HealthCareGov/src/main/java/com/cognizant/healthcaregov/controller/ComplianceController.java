package com.cognizant.healthcaregov.controller;

import com.cognizant.healthcaregov.dto.ComplianceRequestDTO;
import com.cognizant.healthcaregov.dto.ComplianceResponseDTO;
import com.cognizant.healthcaregov.service.ComplianceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/compliance")
public class ComplianceController {

    @Autowired
    private ComplianceService complianceService;

    // Hardcoded for testing until Identity/Login is completed
    private final Integer currentUserId = 1;

    // TODO: Uncomment PreAuthorize when Identity module is integrated
    // @PreAuthorize("hasRole('COMPLIANCE_OFFICER')")
    @PostMapping
    public ResponseEntity<ComplianceResponseDTO> createRecord(@Valid @RequestBody ComplianceRequestDTO requestDTO) {
        ComplianceResponseDTO response = complianceService.createComplianceRecord(requestDTO, currentUserId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // TODO: Uncomment PreAuthorize when Identity module is integrated
    // @PreAuthorize("hasRole('COMPLIANCE_OFFICER')")
    @PutMapping("/{id}")
    public ResponseEntity<ComplianceResponseDTO> updateRecord(@PathVariable Integer id, @Valid @RequestBody ComplianceRequestDTO requestDTO) {
        ComplianceResponseDTO response = complianceService.updateComplianceRecord(id, requestDTO, currentUserId);
        return ResponseEntity.ok(response);
    }

    // NEW: GET Endpoint for COMP-002 Search & Filter
    // TODO: Uncomment PreAuthorize when Identity module is integrated
    // @PreAuthorize("hasRole('COMPLIANCE_OFFICER')")
    @GetMapping
    public ResponseEntity<List<ComplianceResponseDTO>> searchComplianceRecords(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String result,
            @RequestParam(required = false) Integer entityId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<ComplianceResponseDTO> records = complianceService.searchRecords(type, result, entityId, startDate, endDate);
        return ResponseEntity.ok(records);
    }
}