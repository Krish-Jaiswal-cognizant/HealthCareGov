package com.cognizant.healthcaregov.dto;



import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDTO {
    @NotNull(message = "Hospital ID is must and cannot be empty")
    @Positive(message="Hospital ID must be a positive number only")
    private Integer hospitalID;
    @NotNull(message = "Name field cannot be empty")
    private String name;
    @NotNull(message = "Location cannot be empty")
    private String location;
    @NotNull(message = "Capacity cannot be empty")
    @Positive(message = "Cannot be negative")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 10000, message = "Capacity cannot exceed 10,000")
    private Integer capacity;
    @NotNull(message = "Status is required")
    private String status;
}
