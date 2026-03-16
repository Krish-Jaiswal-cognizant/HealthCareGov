package com.cognizant.healthcaregov.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Report")
public class Report
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospitalID")
    private Hospital hospital;

    private String scope;

    @Column(columnDefinition = "json")
    private String metrics;

    @CreationTimestamp
    private LocalDateTime generatedDate;
}
