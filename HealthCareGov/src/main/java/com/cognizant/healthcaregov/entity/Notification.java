package com.cognizant.healthcaregov.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Notification")
public class Notification
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    private Integer entityId;

    @Lob
    @Column(name = "Message", nullable = false)
    private String message;


    @Column(name = "Category", nullable = false)
    private String category;


    @Column(name = "Status")
    private String status;

    @Column(name = "CreatedDate", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;
}
