package com.cognizant.healthcaregov.dto;

public record MedicalSummaryDTO(
        String patientName,
        String contactInfo,
        String medicalDetails, // From detailsJSON
        String overallStatus
) {}