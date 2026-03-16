package com.cognizant.healthcaregov.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = { "patient" })
@Entity
@Table(name = "PatientDocument", uniqueConstraints = {
        @UniqueConstraint(name = "uk_patientdocument_patient", columnNames = { "patientID" })
})
public class PatientDocument
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer documentID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientID", nullable = false, unique = true)
    private Patient patient;

    private String docType;

    private String fileURI;

    @CreationTimestamp
    private LocalDateTime uploadedDate;

    private String verificationStatus;
}
