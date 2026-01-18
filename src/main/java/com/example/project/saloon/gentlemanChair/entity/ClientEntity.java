package com.example.project.saloon.gentlemanChair.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonManagedReference
    private Appointment currentAppointment;

    @OneToOne(mappedBy = "clientEntity", cascade = CascadeType.ALL)
    @JsonBackReference
    private User user;

    @NotNull
    private Integer completedAppointments;

    private String preferBarber;

    @Column(updatable = false, name = "Time")
    private Instant transactionTime = Instant.now();


}
