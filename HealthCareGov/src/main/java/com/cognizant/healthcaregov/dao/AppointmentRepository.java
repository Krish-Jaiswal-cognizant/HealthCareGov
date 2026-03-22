package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    // This tells JPA: "SELECT * FROM appointment WHERE doctor_id = ?"
    List<Appointment> findByDoctorUserID(Integer doctorID);
}