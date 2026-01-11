package com.example.project.saloon.gentlemanChair.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    @NotNull(message = "Email must not be null")
    private String email;

    @NotNull(message = "Password must not be null")
    private String password;


}
