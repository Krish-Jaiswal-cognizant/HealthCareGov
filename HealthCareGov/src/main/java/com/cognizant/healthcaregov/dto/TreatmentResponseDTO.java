package com.cognizant.healthcaregov.dto;
import java.time.LocalDate;

public record TreatmentResponseDTO(
        Integer treatmentId,
        String patientName,
        String doctorName,
        String diagnosis,
        String prescription,
        LocalDate date,
        String status) {}