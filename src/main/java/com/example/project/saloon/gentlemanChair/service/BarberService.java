package com.example.project.saloon.gentlemanChair.service;

import com.example.project.saloon.gentlemanChair.entity.BarberEntity;
import com.example.project.saloon.gentlemanChair.entity.Roles;
import com.example.project.saloon.gentlemanChair.entity.User;
import com.example.project.saloon.gentlemanChair.entity.WorkingDays;
import com.example.project.saloon.gentlemanChair.payload.admin.AllBarberResponseDto;
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

import java.util.List;
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

        BarberEntity barberEntity = BarberEntity
                .builder()
                .experience(requestDto.getExperience())
                .specialization(requestDto.getSpecialization())
                .bio(requestDto.getBio())
                .isAvailable(requestDto.getIsAvailable())
                .workingDays(work)
                .workingHour("9:00 - 19:00")
                .user(user)
                .build();

        user.setBarberEntity(barberEntity);

        User savedUser = userRepository.save(user);

        return BarberResponseDto
                .builder()
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .experience(savedUser.getBarberEntity().getExperience())
                .specialization(savedUser.getBarberEntity().getSpecialization())
                .bio(savedUser.getBarberEntity().getBio())
                .workingHour(savedUser.getBarberEntity().getWorkingHour())
                .isAvailable(savedUser.getBarberEntity().getIsAvailable())
                .phNumber(savedUser.getPhNumber())
                .workingDays(savedUser.getBarberEntity().getWorkingDays())
                .build();

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
