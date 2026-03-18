package com.cognizant.healthcaregov.controller;

import com.cognizant.healthcaregov.dto.ComplianceRequestDTO;
import com.cognizant.healthcaregov.dto.ComplianceResponseDTO;
import com.cognizant.healthcaregov.service.ComplianceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize; // You can leave this imported
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compliance")
public class ComplianceController {

    @Autowired
    private ComplianceService complianceService;

    // POST /api/compliance
    // TODO: Uncomment PreAuthorize when Identity module is integrated
    // @PreAuthorize("hasRole('COMPLIANCE_OFFICER')")
    @PostMapping
    public ResponseEntity<ComplianceResponseDTO> createComplianceRecor (
            @Valid @RequestBody ComplianceRequestDTO requestDTO) {

        Integer currentUserId = 1; // Mock user ID

        ComplianceResponseDTO response = complianceService.createComplianceRecord(requestDTO, currentUserId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // PUT /api/compliance/{id}
    // TODO: Uncomment PreAuthorize when Identity module is integrated
    // @PreAuthorize("hasRole('COMPLIANCE_OFFICER')")
    @PutMapping("/{id}")
    public ResponseEntity<ComplianceResponseDTO> updateComplianceRecord(
            @PathVariable Integer id,
            @Valid @RequestBody ComplianceRequestDTO requestDTO) {

        Integer currentUserId = 1; // Mock user ID

        ComplianceResponseDTO response = complianceService.updateComplianceRecord(id, requestDTO, currentUserId);
        return ResponseEntity.ok(response);
    }
}