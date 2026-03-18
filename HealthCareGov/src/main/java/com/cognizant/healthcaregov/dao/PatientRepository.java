package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}