package com.example.project.saloon.gentlemanChair.payload.barber;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BarberRequestDto {
    @NotNull(message = "Username must not be null")
    private String username;

    @NotNull(message = "Email must not be null")
    private String email;

    @NotNull(message = "Phone Number must not be null")
    @Positive
    private Long phNumber;
}
