package com.example.project.saloon.gentlemanChair.payload.barber;

import com.example.project.saloon.gentlemanChair.entity.WorkingDays;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminEditBarberResponseDto {

    private Boolean isAvailable;
    private String fullName;
    private String workingHour;
    private Set<WorkingDays> workingDays;

}
