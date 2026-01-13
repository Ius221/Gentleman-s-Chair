package com.example.project.saloon.gentlemanChair.controller;

import com.example.project.saloon.gentlemanChair.payload.barber.BarberRequestDto;
import com.example.project.saloon.gentlemanChair.payload.barber.BarberResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class BarberController {

    @PostMapping("/add-details")
    public ResponseEntity<BarberResponseDto> addDetailsToBarber(BarberRequestDto requestDto) {

        return null;
    }
}
