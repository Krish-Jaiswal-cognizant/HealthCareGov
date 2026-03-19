//package com.cognizant.healthcaregov.service;
//
//import com.cognizant.healthcaregov.dao.*;
//import com.cognizant.healthcaregov.entity.*;
//import com.cognizant.healthcaregov.exception.MedicalServiceException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;
//
//@Service
//public class MedicalManagementServiceImpl implements IMedicalManagementService {
//
//    @Autowired
//    private TreatmentRepository treatmentRepo;
//    @Autowired
//    private MedicalRecordRepository recordRepo;
//    @Autowired
//    private PatientRepository patientRepo;
//    @Autowired
//    private UserRepository userRepo;
//
//    @Override
//    @Transactional
//    public Treatment recordTreatment(Treatment treatment) {
//        // 1. Validation
//        if (treatment.getDiagnosis() == null || treatment.getDiagnosis().isBlank()) {
//            throw new MedicalServiceException("Validation Failed: Diagnosis is required.");
//        }
//        if (treatment.getPrescription() == null || treatment.getPrescription().isBlank()) {
//            throw new MedicalServiceException("Validation Failed: Prescription is required.");
//        }
//        // 2. Resolve NullPointerException: Check if objects exist in request
//        if (treatment.getPatient() == null || treatment.getPatient().getPatientID() == null) {
//            throw new MedicalServiceException("Validation Error: Patient ID is missing.");
//        }
//        if (treatment.getDoctor() == null || treatment.getDoctor().getUserID() == null) {
//            throw new MedicalServiceException("Validation Error: Doctor ID is missing.");
//        }
//
//        Integer pId = treatment.getPatient().getPatientID();
//        Integer dId = treatment.getDoctor().getUserID();
//
//        // 3. Verify and Link Entities
//        Patient patient = patientRepo.findById(pId)
//                .orElseThrow(() -> new MedicalServiceException("Patient ID " + pId + " not found."));
//        User doctor = userRepo.findById(dId)
//                .orElseThrow(() -> new MedicalServiceException("Doctor ID " + dId + " not found."));
//
//        treatment.setPatient(patient);
//        treatment.setDoctor(doctor);
//
//        // 4. Save Treatment
//        Treatment savedTreatment = treatmentRepo.save(treatment);
//
//        // 5. Update Summary Record
//        MedicalRecord record = recordRepo.findByPatientPatientID(pId)
//                .orElse(new MedicalRecord());
//
//        if (record.getPatient() == null) record.setPatient(patient);
//
//        String history = record.getDetailsJSON() != null ? record.getDetailsJSON() : "";
//        record.setDetailsJSON(history + " | Treatment: " + treatment.getDiagnosis());
//        record.setStatus(treatment.getStatus());
//
//        recordRepo.save(record);
//        return savedTreatment;
//    }
//
//    @Override
//    public List<Treatment> getPatientHistory(Integer patientId) {
//        if (!patientRepo.existsById(patientId)) {
//            throw new MedicalServiceException("Patient not registered.");
//        }
//        return treatmentRepo.findByPatientPatientID(patientId);
//    }
//
//    @Override
//    public MedicalRecord getSummaryRecord(Integer patientId) {
//        return recordRepo.findByPatientPatientID(patientId)
//                .orElseThrow(() -> new MedicalServiceException("No summary found for Patient ID: " + patientId));
//    }
//
//    @Override
//    @Transactional
//    public Patient updatePatient(Integer patientId, Patient updatedDetails, Integer updaterId) {
//        User updater = userRepo.findById(updaterId)
//                .orElseThrow(() -> new MedicalServiceException("Auth Error: Updater not found."));
//        if (updatedDetails.getName() == null || updatedDetails.getName().isBlank() ||
//                updatedDetails.getContactInfo() == null || updatedDetails.getContactInfo().isBlank()) {
//            throw new MedicalServiceException("Validation Failed: Name and Contact Info are mandatory.");
//        }
//        if (!(updater.getRole().equalsIgnoreCase("DOCTOR") || updater.getRole().equalsIgnoreCase("ADMIN"))) {
//            throw new MedicalServiceException("Access Denied: Doctors/Admins only.");
//        }
//
//        Patient existingPatient = patientRepo.findById(patientId)
//                .orElseThrow(() -> new MedicalServiceException("Patient not found."));
//
//        if ("FINALIZED".equalsIgnoreCase(existingPatient.getStatus())) {
//            throw new MedicalServiceException("Constraint Error: Record is FINALIZED.");
//        }
//
//        existingPatient.setName(updatedDetails.getName());
//        existingPatient.setContactInfo(updatedDetails.getContactInfo());
//        if (updatedDetails.getStatus() != null) existingPatient.setStatus(updatedDetails.getStatus());
//
//        return patientRepo.save(existingPatient);
//    }
//}
package com.cognizant.healthcaregov.service;

import com.cognizant.healthcaregov.dao.*;
import com.cognizant.healthcaregov.dto.*;
import com.cognizant.healthcaregov.entity.*;
import com.cognizant.healthcaregov.exception.MedicalServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalManagementServiceImpl implements IMedicalManagementService {

    private static final Logger logger = LoggerFactory.getLogger(MedicalManagementServiceImpl.class);

    @Autowired private TreatmentRepository treatmentRepo;
    @Autowired private MedicalRecordRepository recordRepo;
    @Autowired private PatientRepository patientRepo;
    @Autowired private UserRepository userRepo;

    @Override
    @Transactional
    public TreatmentResponseDTO recordTreatment(TreatmentRequestDTO dto) {
        logger.info("Service: Received request to record treatment for Patient ID: {}", dto.patientId());

        // 1. Validation
        if (dto.diagnosis() == null || dto.diagnosis().isBlank()) throw new MedicalServiceException("Diagnosis required.");
        if (dto.prescription() == null || dto.prescription().isBlank()) throw new MedicalServiceException("Prescription required.");

        // 2. Fetch Entities
        Patient patient = patientRepo.findById(dto.patientId())
                .orElseThrow(() -> new MedicalServiceException("Patient not found: " + dto.patientId()));
        User doctor = userRepo.findById(dto.doctorId())
                .orElseThrow(() -> new MedicalServiceException("Doctor not found: " + dto.doctorId()));

        // 3. Map & Save Treatment
        Treatment treatment = new Treatment();
        treatment.setPatient(patient);
        treatment.setDoctor(doctor);
        treatment.setDiagnosis(dto.diagnosis());
        treatment.setPrescription(dto.prescription());
        treatment.setStatus(dto.status());
        treatment.setDate(LocalDate.now());

        Treatment saved = treatmentRepo.save(treatment);
        logger.info("Service: Treatment saved successfully. ID: {}", saved.getTreatmentID());

        // 4. Update Summary Record
        MedicalRecord record = recordRepo.findByPatientPatientID(dto.patientId()).orElse(new MedicalRecord());
        if (record.getPatient() == null) record.setPatient(patient);
        String history = record.getDetailsJSON() != null ? record.getDetailsJSON() : "";
        record.setDetailsJSON(history + " | Treatment: " + saved.getDiagnosis());
        record.setStatus(saved.getStatus());
        recordRepo.save(record);

        return new TreatmentResponseDTO(saved.getTreatmentID(), patient.getName(), doctor.getName(),
                saved.getDiagnosis(), saved.getPrescription(), saved.getDate(), saved.getStatus());
    }

    @Override
    public List<TreatmentResponseDTO> getPatientHistory(Integer patientId) {
        logger.info("Service: Fetching treatment history for Patient ID: {}", patientId);

        if (!patientRepo.existsById(patientId)) {
            logger.warn("Service: Attempted to fetch history for non-existent Patient ID: {}", patientId);
            throw new MedicalServiceException("Patient not registered.");
        }

        return treatmentRepo.findByPatientPatientID(patientId).stream()
                .map(t -> new TreatmentResponseDTO(t.getTreatmentID(), t.getPatient().getName(),
                        t.getDoctor().getName(), t.getDiagnosis(), t.getPrescription(), t.getDate(), t.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public MedicalSummaryDTO getSummaryRecord(Integer patientId) {
        logger.info("Service: Generating summary for Patient ID: {}", patientId);

        MedicalRecord record = recordRepo.findByPatientPatientID(patientId)
                .orElseThrow(() -> new MedicalServiceException("No summary found for Patient ID: " + patientId));

        return new MedicalSummaryDTO(record.getPatient().getName(), record.getPatient().getContactInfo(),
                record.getDetailsJSON(), record.getStatus());
    }

    @Override
    @Transactional
    public PatientUpdateDTO updatePatient(Integer patientId, PatientUpdateDTO dto, Integer updaterId) {
        logger.info("Service: Validating update request for Patient ID: {}", patientId);

        // 1. Strict Validation: Throw exception if ANY field is missing
        if (dto.name() == null || dto.name().isBlank()) {
            throw new MedicalServiceException("Update failed: Name is required.");
        }
        if (dto.contactInfo() == null || dto.contactInfo().isBlank()) {
            throw new MedicalServiceException("Update failed: Contact Info is required.");
        }
        if (dto.status() == null || dto.status().isBlank()) {
            throw new MedicalServiceException("Update failed: Status is required.");
        }

        // 2. Security Check (Admin/Doctor Only)
        User updater = userRepo.findById(updaterId)
                .orElseThrow(() -> new MedicalServiceException("User not found."));

        if (!updater.getRole().matches("(?i)DOCTOR|ADMIN")) {
            logger.error("Security Alert: Unauthorized update attempt by User ID: {}", updaterId);
            throw new MedicalServiceException("Access Denied.");
        }

        // 3. Finalized Check
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new MedicalServiceException("Patient not found."));

        if ("FINALIZED".equalsIgnoreCase(patient.getStatus())) {
            throw new MedicalServiceException("Cannot update a FINALIZED record.");
        }

        // 4. Save
        patient.setName(dto.name());
        patient.setContactInfo(dto.contactInfo());
        patient.setStatus(dto.status());

        patientRepo.save(patient);
        logger.info("Service: Patient {} updated successfully.", patientId);
        return dto;
    }
}