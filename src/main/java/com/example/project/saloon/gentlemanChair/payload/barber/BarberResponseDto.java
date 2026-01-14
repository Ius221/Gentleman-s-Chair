package com.example.project.saloon.gentlemanChair.payload.barber;

import com.example.project.saloon.gentlemanChair.entity.Roles;
import com.example.project.saloon.gentlemanChair.entity.WorkingDays;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BarberResponseDto {
    private String username;
    private String email;
    private Roles role;
    private Integer experience;
    private Set<String> specialization;
    private String bio;
    private Boolean isAvailable;
    private Long phNumber;
    private Set<WorkingDays> workingDays;

}
