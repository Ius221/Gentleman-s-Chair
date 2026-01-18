package com.example.project.saloon.gentlemanChair.payload.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditBarberRequestDto {

    @Email
    @NotNull
    private String email;

    @NotNull
    private Boolean isAvailable;

    @NotNull
    private Set<String> workingDays;

    @NotNull
    private String workingHour;
}
