package com.example.project.saloon.gentlemanChair.payload.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllBarberResponseDto {
    private Integer totalNumber;
    private List<String> name;
}
