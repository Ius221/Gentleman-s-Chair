package com.example.project.saloon.gentlemanChair.controller;

import com.example.project.saloon.gentlemanChair.payload.admin.AllBarberResponseDto;
import com.example.project.saloon.gentlemanChair.payload.auth.SignupRequestDto;
import com.example.project.saloon.gentlemanChair.payload.auth.SignupResponseDto;
import com.example.project.saloon.gentlemanChair.payload.admin.EditBarberRequestDto;
import com.example.project.saloon.gentlemanChair.payload.admin.EditBarberResponseDto;
import com.example.project.saloon.gentlemanChair.service.AdminService;
import com.example.project.saloon.gentlemanChair.service.BarberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BarberService barberService;
    @Autowired
    private AdminService adminService;

    @PostMapping("/create-barber")
    public ResponseEntity<SignupResponseDto> createBarber(@Valid @RequestBody SignupRequestDto requestDto) {
        SignupResponseDto responseDto = adminService.createBarber(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/all-barbers")
    public ResponseEntity<AllBarberResponseDto> showAllBarber() {
        AllBarberResponseDto responseDto = adminService.fetchAllBarber();
        return new ResponseEntity<>(responseDto, HttpStatus.FOUND);
    }

    @PutMapping("/edit-barber")
    public ResponseEntity<EditBarberResponseDto> adminEditBarber(@Valid @RequestBody EditBarberRequestDto requestDto) {
        EditBarberResponseDto responseDto = adminService.editBarber(requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
