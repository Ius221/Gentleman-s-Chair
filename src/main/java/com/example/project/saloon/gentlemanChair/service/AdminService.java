package com.example.project.saloon.gentlemanChair.service;

import com.example.project.saloon.gentlemanChair.entity.Roles;
import com.example.project.saloon.gentlemanChair.entity.User;
import com.example.project.saloon.gentlemanChair.payload.admin.AllBarberResponseDto;
import com.example.project.saloon.gentlemanChair.payload.auth.SignupRequestDto;
import com.example.project.saloon.gentlemanChair.payload.auth.SignupResponseDto;
import com.example.project.saloon.gentlemanChair.repository.BarberRepository;
import com.example.project.saloon.gentlemanChair.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BarberRepository barberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public SignupResponseDto createBarber(@Valid SignupRequestDto requestDto) {

        User currUser = userRepository.findByEmail(requestDto.getEmail()).orElse(null);

        if (currUser != null) throw new IllegalArgumentException("Email already exists");

        String randomPassword = UUID.randomUUID().toString().split("-")[0];
        System.out.println(randomPassword);
        User savedUser = userRepository.save(
                User
                        .builder()
                        .username(requestDto.getUsername())
                        .email(requestDto.getEmail())
                        .phNumber(requestDto.getPhNumber())
                        .requiredPasswordChange(true)
                        .password(randomPassword)
                        .role(Roles.MANAGER)
                        .build()
        );

        return new SignupResponseDto(
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole(),
                randomPassword
        );
    }

    public AllBarberResponseDto fetchAllBarber() {

        List<User> allUsers = userRepository.findAll();

        List<User> userThatContainsBarber = allUsers
                .stream()
                .filter(barber -> barber.getRole() == Roles.MANAGER)
                .toList();

        if (userThatContainsBarber.isEmpty()) throw new IllegalArgumentException("No Barber Present");

        return AllBarberResponseDto
                .builder()
                .totalNumber(userThatContainsBarber.size())
                .name(userThatContainsBarber
                        .stream()
                        .map(e -> e.getUsername())
                        .toList()
                )
                .build();
    }
}
