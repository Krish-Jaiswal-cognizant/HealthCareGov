package com.cognizant.healthcaregov.controller;

import com.cognizant.healthcaregov.dto.AppointmentRequestDTO;
import com.cognizant.healthcaregov.entity.Appointment;
import com.cognizant.healthcaregov.service.AppointmentService;
import jakarta.validation.Valid; // Required for @Valid
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<Appointment> bookAppointment(@Valid @RequestBody AppointmentRequestDTO appointmentDTO) {
        // Now passing the DTO instead of the Entity
        Appointment confirmedAppointment = appointmentService.bookAppointment(appointmentDTO);

        // Returns 200 OK with the saved appointment details
        return ResponseEntity.ok(confirmedAppointment);
    }
}