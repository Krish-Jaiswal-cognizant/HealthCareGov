package com.cognizant.healthcaregov.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ComplianceResponseDTO {
    private Integer complianceID;
    private Integer entityId;
    private String type;
    private String result;
    private LocalDate date;
    private String notes;
}