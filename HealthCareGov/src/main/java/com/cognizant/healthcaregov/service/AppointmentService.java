package com.cognizant.healthcaregov.service;

import com.cognizant.healthcaregov.entity.Appointment;
import com.cognizant.healthcaregov.entity.Schedule;
import com.cognizant.healthcaregov.dao.AppointmentRepository;
import com.cognizant.healthcaregov.dao.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter; // Added for time formatting
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private ScheduleRepository scheduleRepo;

    // Define the formatter once to ensure HH:mm:ss format (e.g., 10:00:00)
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Transactional
    public Appointment bookAppointment(Appointment appointment) {
        // Logic: Convert LocalTime to a String that exactly matches the DB "time_slot"
        String formattedTime = appointment.getTime().format(TIME_FORMATTER);

        // Debugging line (Check your IntelliJ console for this when you click Send)
        System.out.println("Searching for: Doctor=" + appointment.getDoctor().getUserID() +
                ", Date=" + appointment.getDate() +
                ", Time=" + formattedTime);

        Optional<Schedule> availableSlot = scheduleRepo.findByDoctorUserIDAndAvailableDateAndTimeSlot(
                appointment.getDoctor().getUserID(),
                appointment.getDate(),
                formattedTime // Using the formatted string here
        );

        if (availableSlot.isPresent() && "Available".equalsIgnoreCase(availableSlot.get().getStatus())) {
            // 1. Mark the slot as Booked
            Schedule schedule = availableSlot.get();
            schedule.setStatus("Booked");
            scheduleRepo.save(schedule);

            // 2. Save the Appointment with 'Confirmed' status
            appointment.setStatus("Confirmed");
            return appointmentRepo.save(appointment);
        } else {
            // If it reaches here, the query returned nothing or the slot isn't 'Available'
            throw new RuntimeException("Error: Selected time slot is not available for this doctor.");
        }
    }
}