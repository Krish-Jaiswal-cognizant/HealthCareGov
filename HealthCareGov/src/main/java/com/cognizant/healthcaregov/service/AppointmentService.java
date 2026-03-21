package com.cognizant.healthcaregov.service;

import com.cognizant.healthcaregov.dto.AppointmentRequestDTO;
import com.cognizant.healthcaregov.entity.*;
import com.cognizant.healthcaregov.dao.*;
import com.cognizant.healthcaregov.exception.SlotUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Slf4j // Lombok: Replaces System.out.println with a professional logger
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private ScheduleRepository scheduleRepo;

    // We need these to fetch the real entities from the IDs in the DTO
    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private UserRepository userRepo; // Assuming this handles Doctor/User entities

    @Autowired
    private HospitalRepository hospitalRepo;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Transactional
    public Appointment bookAppointment(AppointmentRequestDTO dto) {
        // 1. Log the attempt professionally
        log.info("Booking request received - Doctor ID: {}, Date: {}, Time: {}",
                dto.getDoctorID(), dto.getDate(), dto.getTime());

        String formattedTime = dto.getTime().format(TIME_FORMATTER);

        // 2. Check for the available slot
        Optional<Schedule> availableSlot = scheduleRepo.findByDoctorUserIDAndAvailableDateAndTimeSlot(
                dto.getDoctorID(),
                dto.getDate(),
                formattedTime
        );

        if (availableSlot.isPresent() && "Available".equalsIgnoreCase(availableSlot.get().getStatus())) {

            // 3. Fetch full entities to build the Appointment relationship
            Patient patient = patientRepo.findById(dto.getPatientID())
                    .orElseThrow(() -> new RuntimeException("Patient not found"));

            User doctor = userRepo.findById(dto.getDoctorID())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            Hospital hospital = hospitalRepo.findById(dto.getHospitalID())
                    .orElseThrow(() -> new RuntimeException("Hospital not found"));

            // 4. Mark the slot as Booked
            Schedule schedule = availableSlot.get();
            schedule.setStatus("Booked");
            scheduleRepo.save(schedule);

            // 5. Create and Map the Appointment entity
            Appointment appointment = new Appointment();
            appointment.setPatient(patient);
            appointment.setDoctor(doctor);
            appointment.setHospital(hospital);
            appointment.setDate(dto.getDate());
            appointment.setTime(dto.getTime());
            appointment.setStatus("Confirmed");

            log.info("Appointment successfully confirmed for Patient: {}", patient.getName());
            return appointmentRepo.save(appointment);

        } else {
            // 6. Use your new Custom Exception!
            log.warn("Booking failed - Slot unavailable for Doctor ID: {}", dto.getDoctorID());
            throw new SlotUnavailableException("Error: Selected time slot is not available for this doctor.");
        }
    }
}