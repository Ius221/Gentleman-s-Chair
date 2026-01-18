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
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AppointmentEntity> currentAppointmentEntity;

//    @OneToOne(mappedBy = "clientEntity")
    @OneToOne
    @JsonBackReference
    private User user;

//    @OneToMany
//    @JsonManagedReference
//    private List<AppointmentEntity> allAppointment;

    @NotNull
    private Integer completedAppointments;

    private String preferBarber;

    @Column(updatable = false, name = "Time")
    private Instant transactionTime = Instant.now();


}
