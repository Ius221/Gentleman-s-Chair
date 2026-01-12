package com.example.project.saloon.gentlemanChair.service;

import com.example.project.saloon.gentlemanChair.entity.Roles;
import com.example.project.saloon.gentlemanChair.entity.User;
import com.example.project.saloon.gentlemanChair.payload.auth.LoginRequestDto;
import com.example.project.saloon.gentlemanChair.payload.auth.LoginResponseDto;
import com.example.project.saloon.gentlemanChair.payload.auth.SignupRequestDto;
import com.example.project.saloon.gentlemanChair.payload.auth.SignupResponseDto;
import com.example.project.saloon.gentlemanChair.repository.UserRepository;
import com.example.project.saloon.gentlemanChair.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public SignupResponseDto signup(SignupRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail()).orElse(null);

        if (user != null) throw new IllegalArgumentException("Email Already Used");

        Roles role = requestDto.getRole() == null ? Roles.CLIENT :
                switch (requestDto.getRole().toLowerCase()) {
                    case "admin" -> Roles.ADMIN;
                    case "manager" -> Roles.MANAGER;
                    default -> Roles.CLIENT;
                };

        user = userRepository.save(
                User.builder()
                        .username(requestDto.getUsername())
                        .email(requestDto.getEmail())
                        .role(role)
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .build()
        );

        return new SignupResponseDto(user.getUsername(), user.getEmail(), user.getRole());

    }

    public LoginResponseDto login(@Valid LoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        assert user != null;
        String token = jwtUtil.generateJwtToken(user);

        return new LoginResponseDto(token, user.getEmail(), user.getRole());
    }
}
