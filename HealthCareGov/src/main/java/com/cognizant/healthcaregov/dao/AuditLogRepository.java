package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {
}