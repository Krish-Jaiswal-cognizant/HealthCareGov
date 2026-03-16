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
@Table(name = "ComplianceRecord")
public class ComplianceRecord
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer complianceID;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "appointmentID")
//    private Appointment appointment;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "treatmentID")
//    private Treatment treatment;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "hospitalID", nullable = false)
//    private Hospital hospital;
    private Integer entityId;
    private String type;

    private String result;

    @CreationTimestamp
    private LocalDate date;

    private String notes;
}
