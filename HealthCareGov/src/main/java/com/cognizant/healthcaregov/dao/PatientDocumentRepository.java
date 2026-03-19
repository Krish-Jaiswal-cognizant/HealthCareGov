package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.PatientDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientDocumentRepository extends JpaRepository<PatientDocument, Integer> {
}