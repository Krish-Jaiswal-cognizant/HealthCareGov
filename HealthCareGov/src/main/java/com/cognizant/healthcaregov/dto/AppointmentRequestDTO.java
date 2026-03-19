package com.cognizant.healthcaregov.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data // Lombok: Generates getters, setters, and toString automatically
public class AppointmentRequestDTO {
    @NotNull(message = "Patient ID is required")
    private Integer patientID;

    @NotNull(message = "Doctor ID is required")
    private Integer doctorID;

    @NotNull(message = "Hospital ID is required")
    private Integer hospitalID;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Time is required")
    private LocalTime time;
}