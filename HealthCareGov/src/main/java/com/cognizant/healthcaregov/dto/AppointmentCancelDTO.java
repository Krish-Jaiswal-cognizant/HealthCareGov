package com.cognizant.healthcaregov.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppointmentCancelDTO {

    @NotNull(message = "Appointment ID is required")
    @Min(value = 1, message = "Invalid Appointment ID")
    private Integer appointmentID;

    // Optional: You could add a 'reason' string here later!
}