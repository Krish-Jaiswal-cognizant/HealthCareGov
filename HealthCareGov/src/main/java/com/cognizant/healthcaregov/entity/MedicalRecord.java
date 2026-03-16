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
@Table(name = "MedicalRecord")
public class MedicalRecord
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recordID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientID")
    private Patient patient;

//    @Column(columnDefinition = "json")
    private String detailsJSON;

    @CreationTimestamp
    private LocalDate date;

    private String status;
}
