package com.cognizant.healthcaregov.service;

import com.cognizant.healthcaregov.dao.*;
import com.cognizant.healthcaregov.entity.*;
import com.cognizant.healthcaregov.exception.MedicalServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MedicalManagementServiceImpl implements IMedicalManagementService {

    @Autowired
    private TreatmentRepository treatmentRepo;

    @Autowired
    private MedicalRecordRepository recordRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private UserRepository userRepo; // Injecting your new repository

    @Override
    @Transactional
    public Treatment recordTreatment(Treatment treatment) {
        // 1. Input Validation
        if (treatment.getDiagnosis() == null || treatment.getDiagnosis().isBlank()) {
            throw new MedicalServiceException("Validation Failed: Diagnosis is required.");
        }
        if (treatment.getPrescription() == null || treatment.getPrescription().isBlank()) {
            throw new MedicalServiceException("Validation Failed: Prescription is required.");
        }

        // 2. Security Check: Verify Patient Exists
        Integer pId = treatment.getPatient().getPatientID();
        if (!patientRepo.existsById(pId)) {
            throw new MedicalServiceException("Security Error: Patient ID " + pId + " is not registered.");
        }

        // 3. Security Check: Verify Doctor (User) Exists
        Integer dId = treatment.getDoctor().getUserID();
        if (!userRepo.existsById(dId)) {
            throw new MedicalServiceException("Validation Error: Doctor ID " + dId + " does not exist in the system.");
        }

        // 4. Save Treatment session
        Treatment savedTreatment = treatmentRepo.save(treatment);

        // 5. Update Outcome Tracking in MedicalRecord
        MedicalRecord record = recordRepo.findByPatientPatientID(pId)
                .orElse(new MedicalRecord());

        if (record.getPatient() == null) {
            record.setPatient(treatment.getPatient());
        }

        String history = record.getDetailsJSON() != null ? record.getDetailsJSON() : "";
        record.setDetailsJSON(history + " | Treatment: " + treatment.getDiagnosis());
        record.setStatus(treatment.getStatus());

        recordRepo.save(record);

        return savedTreatment;
    }

    @Override
    public List<Treatment> getPatientHistory(Integer patientId) {
        if (!patientRepo.existsById(patientId)) {
            throw new MedicalServiceException("Access Denied: Patient is not registered in our records.");
        }
        return treatmentRepo.findByPatientPatientID(patientId);
    }

    @Override
    public MedicalRecord getSummaryRecord(Integer patientId) {
        return recordRepo.findByPatientPatientID(patientId)
                .orElseThrow(() -> new MedicalServiceException("No summary found for Patient ID: " + patientId));
    }

    @Override
    @Transactional
    public Patient updatePatient(Integer patientId, Patient updatedDetails, Integer updaterId) {
        // 1. Role Check: Only Doctor or Admin can update
        User updater = userRepo.findById(updaterId)
                .orElseThrow(() -> new MedicalServiceException("Auth Error: Updater User ID not found."));

        String role = updater.getRole().toUpperCase();
        if (!(role.equals("DOCTOR") || role.equals("ADMIN"))) {
            throw new MedicalServiceException("Access Denied: Only Doctors or Admins can update patient details.");
        }

        // 2. Fetch existing Patient
        Patient existingPatient = patientRepo.findById(patientId)
                .orElseThrow(() -> new MedicalServiceException("Data Error: Patient not found."));

        // 3. Finalized Check: If status is 'FINALIZED', block all updates
        if ("FINALIZED".equalsIgnoreCase(existingPatient.getStatus())) {
            throw new MedicalServiceException("Constraint Error: Cannot update. This patient record has been FINALIZED.");
        }

        // 4. Validation: Mandatory fields check
        if (updatedDetails.getName() == null || updatedDetails.getName().isBlank() ||
                updatedDetails.getContactInfo() == null || updatedDetails.getContactInfo().isBlank()) {
            throw new MedicalServiceException("Validation Failed: Name and Contact Info are mandatory.");
        }

        // 5. Update fields
        existingPatient.setName(updatedDetails.getName());
        existingPatient.setAddress(updatedDetails.getAddress());
        existingPatient.setContactInfo(updatedDetails.getContactInfo());
        existingPatient.setGender(updatedDetails.getGender());
        existingPatient.setDob(updatedDetails.getDob());

        // Allow status update (e.g., changing from ACTIVE to FINALIZED)
        if (updatedDetails.getStatus() != null) {
            existingPatient.setStatus(updatedDetails.getStatus());
        }

        return patientRepo.save(existingPatient);
    }
}