package com.example.project.saloon.gentlemanChair.payload.auth;

import com.example.project.saloon.gentlemanChair.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private String username;
    private Roles role;
}
