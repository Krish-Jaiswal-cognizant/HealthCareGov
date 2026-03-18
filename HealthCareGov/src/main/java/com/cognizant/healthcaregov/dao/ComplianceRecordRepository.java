package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.ComplianceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplianceRecordRepository extends JpaRepository<ComplianceRecord, Integer> {
    // No custom queries needed for COMP-001. Search/Filter will be added here in COMP-002.
}