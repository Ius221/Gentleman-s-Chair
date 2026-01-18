package com.example.project.saloon.gentlemanChair.service;

import com.example.project.saloon.gentlemanChair.entity.Roles;
import com.example.project.saloon.gentlemanChair.entity.User;
import com.example.project.saloon.gentlemanChair.entity.WorkingDays;
import com.example.project.saloon.gentlemanChair.payload.admin.AllBarberResponseDto;
import com.example.project.saloon.gentlemanChair.payload.auth.SignupRequestDto;
import com.example.project.saloon.gentlemanChair.payload.auth.SignupResponseDto;
import com.example.project.saloon.gentlemanChair.payload.admin.EditBarberRequestDto;
import com.example.project.saloon.gentlemanChair.payload.admin.EditBarberResponseDto;
import com.example.project.saloon.gentlemanChair.repository.BarberRepository;
import com.example.project.saloon.gentlemanChair.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public EditBarberResponseDto editBarber(@Valid EditBarberRequestDto requestDto) {

        User currUser = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + requestDto.getEmail()));

        Set<WorkingDays> workingDays = requestDto.getWorkingDays()
                .stream()
                .map(day -> switch (day.toLowerCase()) {
                    case "monday", "mon" -> WorkingDays.MONDAY;
                    case "tuesday", "tue" -> WorkingDays.TUESDAY;
                    case "wednesday", "wed" -> WorkingDays.WEDNESDAY;
                    case "thursday", "thurs" -> WorkingDays.THURSDAY;
                    case "friday", "fri" -> WorkingDays.FRIDAY;
                    case "saturday", "sat" -> WorkingDays.SATURDAY;
                    case "sunday", "sun" -> WorkingDays.SUNDAY;
                    default -> throw new IllegalArgumentException("Unexpected value: " + day);
                })
                .collect(Collectors.toSet());

        currUser.getBarber().setIsAvailable(requestDto.getIsAvailable());
        currUser.getBarber().setWorkingDays(workingDays);
        currUser.getBarber().setWorkingHour(requestDto.getWorkingHour());

        currUser = userRepository.save(currUser);


        return EditBarberResponseDto
                .builder()
                .isAvailable(currUser.getBarber().getIsAvailable())
                .fullName(currUser.getUsername())
                .workingHour(currUser.getBarber().getWorkingHour())
                .workingDays(currUser.getBarber().getWorkingDays())
                .build();
    }
}
