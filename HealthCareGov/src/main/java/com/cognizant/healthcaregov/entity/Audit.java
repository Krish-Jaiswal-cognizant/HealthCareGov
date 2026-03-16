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
@Table(name = "Audit")
public class Audit
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer auditID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officerID")
    private User officer;

    private String scope;
    private String findings;


    @CreationTimestamp
    private LocalDate date;

    private String status;
}
