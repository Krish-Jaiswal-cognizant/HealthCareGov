package com.cognizant.healthcaregov.dto;

public record TreatmentRequestDTO(
        Integer patientId,
        Integer doctorId,
        String diagnosis,
        String prescription,
        String status
) {}