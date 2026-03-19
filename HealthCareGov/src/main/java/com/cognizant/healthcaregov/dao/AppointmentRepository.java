package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}