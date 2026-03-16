package com.cognizant.healthcaregov.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Treatment")
public class Treatment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer treatmentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientID")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorID")
    private User doctor;


    private String diagnosis;


    private String prescription;

    @CreationTimestamp
    private LocalDate date;

    private String status;
}
