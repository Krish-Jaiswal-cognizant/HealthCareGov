package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Integer> {
}