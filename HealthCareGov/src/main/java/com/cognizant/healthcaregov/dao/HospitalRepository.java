package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    List<Hospital> findByNameContainingIgnoreCase(String name);

    List<Hospital> findByLocationContainingIgnoreCase(String location);

    @Query("SELECT h FROM Hospital h WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(h.location) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Hospital> searchHospitals(@Param("query") String query);
}