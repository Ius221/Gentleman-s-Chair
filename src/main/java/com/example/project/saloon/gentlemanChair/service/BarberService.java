package com.example.project.saloon.gentlemanChair.service;

import com.example.project.saloon.gentlemanChair.entity.Barber;
import com.example.project.saloon.gentlemanChair.entity.Roles;
import com.example.project.saloon.gentlemanChair.entity.User;
import com.example.project.saloon.gentlemanChair.payload.barber.BarberRequestDto;
import com.example.project.saloon.gentlemanChair.payload.barber.BarberResponseDto;
import com.example.project.saloon.gentlemanChair.repository.BarberRepository;
import com.example.project.saloon.gentlemanChair.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarberService {

    @Autowired
    private BarberRepository barberRepository;
    @Autowired
    private UserRepository userRepository;

    public BarberResponseDto createBarber(@Valid BarberRequestDto requestDto) {
        if (!userRepository.findByEmail(requestDto.getEmail()).isEmpty())
            throw new IllegalArgumentException("Email already in-use");

        Barber barber = barberRepository.save(
                Barber
                        .builder()
                        .experience(requestDto.getExperience())
                        .specialization(requestDto.getSpecialization())
                        .bio(requestDto.getBio())
                        .build()
        );

        User savedUser = userRepository.save(
                User
                        .builder()
                        .username(requestDto.getUsername())
                        .email(requestDto.getEmail())
                        .phNumber(requestDto.getPhNumber())
                        .password(requestDto.getPassword())
                        .role(Roles.MANAGER)
                        .barber(barber)
                        .build()
        );

        return new BarberResponseDto(
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getPhNumber(),
                savedUser.getPassword(),
                savedUser.getBarber().getExperience(),
                savedUser.getBarber().getSpecialization(),
                savedUser.getRole(),
                savedUser.getBarber().getBio()
        );
    }
}
