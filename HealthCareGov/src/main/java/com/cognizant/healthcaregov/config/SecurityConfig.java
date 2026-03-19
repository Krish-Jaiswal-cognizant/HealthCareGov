package com.cognizant.healthcaregov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // This is the magic annotation that enables @PreAuthorize
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF so Postman requests aren't blocked
                .authorizeHttpRequests(auth -> auth
                        // Temporarily allow all requests so you can test your endpoints locally.
                        // You and your team will tighten this up later when the User/Login module is done.
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}