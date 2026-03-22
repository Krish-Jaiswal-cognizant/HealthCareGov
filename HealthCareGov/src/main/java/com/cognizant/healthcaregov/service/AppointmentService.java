package com.cognizant.healthcaregov.service;

import com.cognizant.healthcaregov.dto.AppointmentRequestDTO;
import com.cognizant.healthcaregov.dto.AppointmentCancelDTO;
import com.cognizant.healthcaregov.dto.DoctorScheduleDTO;
import com.cognizant.healthcaregov.entity.*;
import com.cognizant.healthcaregov.dao.*;
import com.cognizant.healthcaregov.exception.SlotUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private ScheduleRepository scheduleRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private HospitalRepository hospitalRepo;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Transactional
    public Appointment bookAppointment(AppointmentRequestDTO dto) {
        log.info("Booking request received - Doctor ID: {}, Date: {}, Time: {}",
                dto.getDoctorID(), dto.getDate(), dto.getTime());

        String formattedTime = dto.getTime().format(TIME_FORMATTER);

        Optional<Schedule> availableSlot = scheduleRepo.findByDoctorUserIDAndAvailableDateAndTimeSlot(
                dto.getDoctorID(),
                dto.getDate(),
                formattedTime
        );

        if (availableSlot.isPresent() && "Available".equalsIgnoreCase(availableSlot.get().getStatus())) {

            Patient patient = patientRepo.findById(dto.getPatientID())
                    .orElseThrow(() -> new RuntimeException("Patient not found"));

            User doctor = userRepo.findById(dto.getDoctorID())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            Hospital hospital = hospitalRepo.findById(dto.getHospitalID())
                    .orElseThrow(() -> new RuntimeException("Hospital not found"));

            Schedule schedule = availableSlot.get();
            schedule.setStatus("Booked");
            scheduleRepo.save(schedule);

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
            log.warn("Booking failed - Slot unavailable for Doctor ID: {}", dto.getDoctorID());
            throw new SlotUnavailableException("Error: Selected time slot is not available for this doctor.");
        }
    }

    @Transactional
    public void cancelAppointment(AppointmentCancelDTO cancelDTO) {
        Appointment appointment = appointmentRepo.findById(cancelDTO.getAppointmentID())
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + cancelDTO.getAppointmentID()));

        if ("Cancelled".equalsIgnoreCase(appointment.getStatus())) {
            throw new RuntimeException("Appointment is already cancelled.");
        }

        String formattedTime = appointment.getTime().format(TIME_FORMATTER);

        Optional<Schedule> scheduleSlot = scheduleRepo.findByDoctorUserIDAndAvailableDateAndTimeSlot(
                appointment.getDoctor().getUserID(),
                appointment.getDate(),
                formattedTime
        );

        scheduleSlot.ifPresent(slot -> {
            slot.setStatus("Available");
            scheduleRepo.save(slot);
            log.info("Schedule slot freed up for Doctor ID: {}", appointment.getDoctor().getUserID());
        });

        appointment.setStatus("Cancelled");
        appointmentRepo.save(appointment);

        log.info("Appointment ID: {} has been successfully cancelled.", appointment.getAppointmentID());
    }

    public List<DoctorScheduleDTO> getDoctorSchedule(Integer doctorID) {
        // 1. Fetch appointments for this specific doctor
        List<Appointment> appointments = appointmentRepo.findByDoctorUserID(doctorID);

        // 2. Convert to DTO using the single 'getName()' field
        return appointments.stream()
                .map(app -> new DoctorScheduleDTO(
                        app.getAppointmentID(),
                        app.getPatient().getName(), // Using single name field here
                        app.getDate(),
                        app.getTime(),
                        app.getStatus()
                ))
                .collect(Collectors.toList());
    }
}