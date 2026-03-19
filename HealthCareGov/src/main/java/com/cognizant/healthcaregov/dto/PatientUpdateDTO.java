package com.cognizant.healthcaregov.dto;

public record PatientUpdateDTO(
        String name,
        String address,
        String contactInfo,
        String status
) {}