package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.ComplianceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplianceRecordRepository extends JpaRepository<ComplianceRecord, Integer> {
}