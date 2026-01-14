package com.example.project.saloon.gentlemanChair.service;

import com.example.project.saloon.gentlemanChair.entity.Barber;
import com.example.project.saloon.gentlemanChair.entity.User;
import com.example.project.saloon.gentlemanChair.entity.WorkingDays;
import com.example.project.saloon.gentlemanChair.payload.barber.BarberRequestDto;
import com.example.project.saloon.gentlemanChair.payload.barber.BarberResponseDto;
import com.example.project.saloon.gentlemanChair.payload.barber.ChangePasswordRequest;
import com.example.project.saloon.gentlemanChair.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BarberService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public ResponseEntity<String> changePassword(ChangePasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        if (!request.getOldPassword().equals(user.getPassword())) {
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

    public BarberResponseDto addDetails(BarberRequestDto requestDto) {

        User user = userRepository
                .findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found with Email: " + requestDto.getEmail()
                ));

        if (user.getRequiredPasswordChange()) throw new IllegalArgumentException("Change Password First");

        Set<WorkingDays> work = Set.of(
                WorkingDays.MONDAY,
                WorkingDays.TUESDAY,
                WorkingDays.WEDNESDAY,
                WorkingDays.THURSDAY,
                WorkingDays.FRIDAY,
                WorkingDays.SUNDAY
        );

        Barber barber = Barber
                .builder()
                .experience(requestDto.getExperience())
                .specialization(requestDto.getSpecialization())
                .bio(requestDto.getBio())
                .isAvailable(requestDto.getIsAvailable())
                .workingDays(work)
                .user(user)
                .build();

        user.setBarber(barber);

        User savedUser = userRepository.save(user);

        return BarberResponseDto
                .builder()
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .experience(savedUser.getBarber().getExperience())
                .specialization(savedUser.getBarber().getSpecialization())
                .bio(savedUser.getBarber().getBio())
                .isAvailable(savedUser.getBarber().getIsAvailable())
                .phNumber(savedUser.getPhNumber())
                .workingDays(savedUser.getBarber().getWorkingDays())
                .build();

    }
}
