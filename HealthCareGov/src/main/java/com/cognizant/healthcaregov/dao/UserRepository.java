package com.cognizant.healthcaregov.dao;

import com.cognizant.healthcaregov.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}