package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}