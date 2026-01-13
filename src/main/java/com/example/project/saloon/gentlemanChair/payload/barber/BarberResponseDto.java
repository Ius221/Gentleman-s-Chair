package com.example.project.saloon.gentlemanChair.payload.barber;

import com.example.project.saloon.gentlemanChair.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class BarberResponseDto {
    private String username;
    private String email;
    private Roles role;
}
