package com.cognizant.healthcaregov.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AuditLog")
public class AuditLog
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer auditLogID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditID")
    private Audit audit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
    private User user;

    private String action;

    private String resource;

    @Column(name = "timestamp")
    @CreationTimestamp
    private Instant timestamp;
}
