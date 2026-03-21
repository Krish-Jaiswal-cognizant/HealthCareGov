package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    // Renamed from 'DoctorID' to 'DoctorUserID' to match the path: Schedule.doctor -> User.userID
    Optional<Schedule> findByDoctorUserIDAndAvailableDateAndTimeSlot(Integer userID, LocalDate availableDate, String timeSlot);
}