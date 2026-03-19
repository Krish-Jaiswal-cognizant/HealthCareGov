package com.cognizant.healthcaregov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ComplianceRequestDTO {
    @NotNull(message = "Entity ID cannot be null")
    private Integer entityId;

    @NotBlank(message = "Type must be Appointment, Treatment, or Hospital")
    private String type; // Allows selecting Type [cite: 4]

    @NotBlank(message = "Result cannot be blank")
    private String result;

    private String notes;
}