package com.cognizant.healthcaregov.service;

import com.cognizant.healthcaregov.dto.*;
import java.util.List;

public interface IMedicalManagementService {
    TreatmentResponseDTO recordTreatment(TreatmentRequestDTO dto);
    List<TreatmentResponseDTO> getPatientHistory(Integer patientId);
    MedicalSummaryDTO getSummaryRecord(Integer patientId);
    PatientUpdateDTO updatePatient(Integer patientId, PatientUpdateDTO dto, Integer updaterId);
}