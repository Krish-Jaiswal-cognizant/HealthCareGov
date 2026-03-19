package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRepository extends JpaRepository<Treatment, Integer> {
}