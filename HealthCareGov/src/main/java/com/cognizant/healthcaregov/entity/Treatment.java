package com.cognizant.healthcaregov.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer treatmentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientID", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorID", nullable = false)
    private User doctor;

    @Column(nullable = false)
    private String diagnosis;

    @Column(nullable = false)
    private String prescription;

    // Added to satisfy "Doctor must be able to enter treatment notes"
    @Column(columnDefinition = "TEXT")
    private String treatmentNotes;

    @CreationTimestamp
    private LocalDate date;

    private String status; // e.g., "ACTIVE", "COMPLETED"
}