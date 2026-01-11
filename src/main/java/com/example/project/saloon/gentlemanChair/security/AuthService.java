package com.example.project.saloon.gentlemanChair.security;

import com.example.project.saloon.gentlemanChair.entity.User;
import com.example.project.saloon.gentlemanChair.payload.LoginRequestDto;
import com.example.project.saloon.gentlemanChair.payload.LoginResponseDto;
import com.example.project.saloon.gentlemanChair.payload.SignupRequestDto;
import com.example.project.saloon.gentlemanChair.payload.SignupResponseDto;
import com.example.project.saloon.gentlemanChair.repository.UserRepository;
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

        user = userRepository.save(
                User.builder()
                        .username(requestDto.getUsername())
                        .email(requestDto.getEmail())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .build()
        );

        return new SignupResponseDto(user.getUsername(), user.getEmail());

    }

    public LoginResponseDto login(@Valid LoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       requestDto.getEmail(),
                       requestDto.getPassword()
               )
        );

        User user = (User) authentication.getPrincipal();

        String token = jwtUtil.generateJwtToken(user);

        return new LoginResponseDto(token, user.getEmail());
    }
}
