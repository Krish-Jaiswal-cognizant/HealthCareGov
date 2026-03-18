package com.cognizant.healthcaregov.service;

import com.cognizant.healthcaregov.entity.Appointment;
import com.cognizant.healthcaregov.entity.Schedule;
import com.cognizant.healthcaregov.dao.AppointmentRepository;
import com.cognizant.healthcaregov.dao.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private ScheduleRepository scheduleRepo;

    @Transactional
    public Appointment bookAppointment(Appointment appointment) {
        // Logic: Find the doctor's specific slot in the schedule
        // In AppointmentService.java, change the call to:
        Optional<Schedule> availableSlot = scheduleRepo.findByDoctorUserIDAndAvailableDateAndTimeSlot(
                appointment.getDoctor().getUserID(),
                appointment.getDate(),
                appointment.getTime().toString()
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
            throw new RuntimeException("Error: Selected time slot is not available for this doctor.");
        }
    }
}