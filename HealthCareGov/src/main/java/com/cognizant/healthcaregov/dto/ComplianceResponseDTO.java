package com.cognizant.healthcaregov.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ComplianceResponseDTO {
    private Integer complianceID; // Kept capital D based on previous fixes
    private Integer entityId;
    private String type;
    private LocalDate date;
    private String result;
    private String notes;
}