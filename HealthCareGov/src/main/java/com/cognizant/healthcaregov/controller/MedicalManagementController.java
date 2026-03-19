package com.cognizant.healthcaregov.controller;

import com.cognizant.healthcaregov.entity.*;
import com.cognizant.healthcaregov.service.IMedicalManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/medical")
@CrossOrigin(origins = "*")
public class MedicalManagementController {

    @Autowired
    private IMedicalManagementService medicalService;

    @PostMapping("/treatment")
    public ResponseEntity<Treatment> recordNewTreatment(@RequestBody Treatment treatment) {
        // This will now trigger the MedicalServiceException if diagnosis is ""
        Treatment saved = medicalService.recordTreatment(treatment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/history/{patientId}")
    public ResponseEntity<List<Treatment>> getPatientHistory(@PathVariable Integer patientId) {
        List<Treatment> history = medicalService.getPatientHistory(patientId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/summary/{patientId}")
    public ResponseEntity<MedicalRecord> getMedicalSummary(@PathVariable Integer patientId) {
        MedicalRecord record = medicalService.getSummaryRecord(patientId);
        return ResponseEntity.ok(record);
    }

    // PUT: http://localhost:9090/api/v1/medical/patient/1?updaterId=200
    @PutMapping("/patient/{patientId}")
    public ResponseEntity<Patient> updatePatient(
            @PathVariable Integer patientId,
            @RequestBody Patient patientDetails,
            @RequestParam Integer updaterId) {

        Patient updated = medicalService.updatePatient(patientId, patientDetails, updaterId);
        return ResponseEntity.ok(updated);
    }
}