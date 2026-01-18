package com.example.project.saloon.gentlemanChair.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "Customer")
    private ClientEntity clientEntity;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "Barber")
    private BarberEntity barberEntity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
}
