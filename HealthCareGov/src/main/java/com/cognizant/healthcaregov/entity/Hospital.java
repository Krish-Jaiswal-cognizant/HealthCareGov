package com.cognizant.healthcaregov.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Hospital")
public class Hospital
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hospitalID;

    private String name;

    private String location;

    private Integer capacity;

    private String status;
}
