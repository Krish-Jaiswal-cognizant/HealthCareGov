package com.cognizant.healthcaregov.service;
import com.cognizant.healthcaregov.entity.Patient;
import com.cognizant.healthcaregov.entity.Treatment;
import com.cognizant.healthcaregov.entity.MedicalRecord;
import java.util.List;

public interface IMedicalManagementService {
    // Primary action: A doctor records a new treatment session
    Treatment recordTreatment(Treatment treatment);

    // Retrieval: Get all history for a specific patient
    List<Treatment> getPatientHistory(Integer patientId);

    // Retrieval: Get the summary medical record
    MedicalRecord getSummaryRecord(Integer patientId);

    Patient updatePatient(Integer patientId, Patient updatedDetails, Integer updaterId);
}