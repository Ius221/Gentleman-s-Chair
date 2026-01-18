package com.example.project.saloon.gentlemanChair.payload.client;

import com.example.project.saloon.gentlemanChair.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookAppointmentResponseDto {
    @NotNull
    private Status appointmentStatus;

    @NotNull
    private String barberName;

    @NotNull
    private String barberExp;

    @NotNull
    private List<String> services;

    @NotNull
    private String time;

    @NotNull
    private String date;
}

