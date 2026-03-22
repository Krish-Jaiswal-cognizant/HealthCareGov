package com.cognizant.healthcaregov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class DoctorScheduleDTO {
    private Integer appointmentID;
    private String patientName;
    private LocalDate date;
    private LocalTime time;
    private String status;
}