package com.example.project.saloon.gentlemanChair.payload.barber;

import com.example.project.saloon.gentlemanChair.entity.WorkingDays;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BarberRequestDto {
    @NotNull(message = "Username must not be null")
    private Set<String> specialization;

    @NotNull(message = "Email must not be null")
    private String email;

    @NotNull
    private String bio;

    @NotNull(message = "Phone Number must not be null")
    @PositiveOrZero
    private Integer experience;

    @NotNull
    private Boolean isAvailable;

}
