package com.example.project.saloon.gentlemanChair.controller;

import com.example.project.saloon.gentlemanChair.payload.auth.SignupRequestDto;
import com.example.project.saloon.gentlemanChair.payload.auth.SignupResponseDto;
import com.example.project.saloon.gentlemanChair.payload.barber.BarberRequestDto;
import com.example.project.saloon.gentlemanChair.payload.barber.BarberResponseDto;
import com.example.project.saloon.gentlemanChair.service.BarberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BarberService barberService;

    @PostMapping("/create-barber")
    public ResponseEntity<SignupResponseDto> createBarber(@Valid @RequestBody SignupRequestDto requestDto) {
        SignupResponseDto responseDto = barberService.createBarber(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
