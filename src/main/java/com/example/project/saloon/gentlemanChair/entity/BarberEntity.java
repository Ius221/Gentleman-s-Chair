package com.example.project.saloon.gentlemanChair.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BarberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @PositiveOrZero
    private Integer experience;

    @NotNull
    private Set<String> specialization;

    @NotNull
    private String bio;

    @NotNull
    private Boolean isAvailable;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Set<WorkingDays> workingDays;

    @OneToOne(mappedBy = "barberEntity")
    @JsonBackReference
    private User user;

    @NotNull
    private String workingHour;

    //    @CreationTimestamp
    @Column(updatable = false,name = "Time")
    private Instant transactionTime= Instant.now();
}
