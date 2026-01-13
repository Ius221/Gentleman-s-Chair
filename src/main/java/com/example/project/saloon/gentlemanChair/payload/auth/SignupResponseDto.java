package com.example.project.saloon.gentlemanChair.payload.auth;

import com.example.project.saloon.gentlemanChair.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponseDto {
    private String username;
    private String email;
    private Roles userRole;

    private String password; //Only for Barber created by Admin
}
