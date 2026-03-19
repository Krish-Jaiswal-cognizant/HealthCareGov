package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}