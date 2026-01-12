package com.example.project.saloon.gentlemanChair.payload.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
    @NotNull(message = "Username must not be null")
    private String username;

    @NotNull(message = "Email must not be null")
    private String email;

    @NotNull(message = "Password must not be null")
    private String password;

    @NotNull(message = "Phone Number must not be null")
    private Long phNumber;

}
