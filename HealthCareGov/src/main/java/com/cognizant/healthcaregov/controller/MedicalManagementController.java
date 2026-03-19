package com.cognizant.healthcaregov.controller;

import com.cognizant.healthcaregov.dto.*;
import com.cognizant.healthcaregov.exception.GlobalExceptionHandler;
import com.cognizant.healthcaregov.service.IMedicalManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/medical")
@CrossOrigin(origins = "*")
public class MedicalManagementController {
    private static final Logger logger = LoggerFactory.getLogger(MedicalManagementController.class);

    @Autowired private IMedicalManagementService medicalService;

    @PostMapping("/treatment")
    public ResponseEntity<TreatmentResponseDTO> recordNewTreatment(@RequestBody TreatmentRequestDTO dto) {
        // Log that a request has arrived
        logger.info("REST Request: POST /api/v1/medical/treatment received for Patient ID: {}", dto.patientId());

        TreatmentResponseDTO response = medicalService.recordTreatment(dto);

        // Log that the response is ready
        logger.info("REST Response: Treatment recorded successfully for Patient ID: {}", dto.patientId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/history/{patientId}")
    public ResponseEntity<List<TreatmentResponseDTO>> getPatientHistory(@PathVariable Integer patientId) {
        return ResponseEntity.ok(medicalService.getPatientHistory(patientId));
    }

    @GetMapping("/summary/{patientId}")
    public ResponseEntity<MedicalSummaryDTO> getMedicalSummary(@PathVariable Integer patientId) {
        return ResponseEntity.ok(medicalService.getSummaryRecord(patientId));
    }

    @PutMapping("/patient/{patientId}")
    public ResponseEntity<PatientUpdateDTO> updatePatient(
            @PathVariable Integer patientId,
            @RequestBody PatientUpdateDTO dto,
            @RequestParam Integer updaterId) {
        return ResponseEntity.ok(medicalService.updatePatient(patientId, dto, updaterId));
    }
}