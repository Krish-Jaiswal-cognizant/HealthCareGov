package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {
}