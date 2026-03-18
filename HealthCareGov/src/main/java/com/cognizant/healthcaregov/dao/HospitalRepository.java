package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
}