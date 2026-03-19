package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    // This gives you .existsById() and .findById() automatically!
}