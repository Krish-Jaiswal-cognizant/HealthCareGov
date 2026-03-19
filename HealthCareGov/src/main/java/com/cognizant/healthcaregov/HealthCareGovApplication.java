package com.cognizant.healthcaregov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.cognizant.healthcaregov.dao")
public class HealthCareGovApplication {
	public static void main(String[] args) {
		SpringApplication.run(HealthCareGovApplication.class, args);
	}
}