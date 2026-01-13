package com.example.project.saloon.gentlemanChair.service;

import com.example.project.saloon.gentlemanChair.entity.Roles;
import com.example.project.saloon.gentlemanChair.entity.User;
import com.example.project.saloon.gentlemanChair.payload.barber.BarberRequestDto;
import com.example.project.saloon.gentlemanChair.payload.barber.BarberResponseDto;
import com.example.project.saloon.gentlemanChair.payload.barber.ChangePasswordRequest;
import com.example.project.saloon.gentlemanChair.repository.BarberRepository;
import com.example.project.saloon.gentlemanChair.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BarberService {

    @Autowired
    private BarberRepository barberRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public BarberResponseDto createBarber(@Valid BarberRequestDto requestDto) {
        if (!userRepository.findByEmail(requestDto.getEmail()).isEmpty())
            throw new IllegalArgumentException("Email already in-use");

        String randomPassword = UUID.randomUUID().toString().split("-")[0];

        User savedUser = userRepository.save(
                User
                        .builder()
                        .username(requestDto.getUsername())
                        .email(requestDto.getEmail())
                        .phNumber(requestDto.getPhNumber())
                        .requiredPasswordChange(true)
                        .password(passwordEncoder.encode(randomPassword))
                        .role(Roles.MANAGER)
                        .build()
        );

        return new BarberResponseDto(
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getPhNumber(),
                randomPassword,
                savedUser.getRole()
        );
    }

    public ResponseEntity<String> changePassword(ChangePasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The temporary password you entered is incorrect.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setRequiredPasswordChange(false);
        userRepository.save(user);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities()
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body("Password changed successfully! You now have full access.");
    }
}
