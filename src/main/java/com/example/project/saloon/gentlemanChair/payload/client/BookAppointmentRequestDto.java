package com.example.project.saloon.gentlemanChair.payload.client;

import jakarta.validation.constraints.Email;
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
public class BookAppointmentRequestDto {

    @NotNull
    @Email
    private String barberEmail;

    @NotNull
    private String userEmail;

    @NotNull
    private List<String> services;

    @NotNull
    private String date;

    @NotNull
    private String time;
}
