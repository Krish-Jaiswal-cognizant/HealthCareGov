package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {
}