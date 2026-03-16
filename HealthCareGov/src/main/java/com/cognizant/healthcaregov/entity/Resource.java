package com.cognizant.healthcaregov.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Resource")
public class Resource
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resourceID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospitalID")
    private Hospital hospital;

    private String type;

    private Integer quantity;

    private String status;
}
