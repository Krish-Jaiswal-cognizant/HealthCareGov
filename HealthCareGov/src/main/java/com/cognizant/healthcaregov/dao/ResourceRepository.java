package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {

    List<Resource> findByHospital_HospitalID(Integer hospitalId);

    List<Resource> findByType(String type);

    @Query("SELECT r FROM Resource r WHERE r.hospital.hospitalID = :hospitalId AND r.type = :type")
    List<Resource> findByHospitalAndType(@Param("hospitalId") Integer hospitalId, @Param("type") String type);

    @Query("SELECT SUM(r.quantity) FROM Resource r WHERE r.type = :type")
    Long sumQuantityByType(@Param("type") String type);
}