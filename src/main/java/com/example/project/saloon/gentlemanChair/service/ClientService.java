package com.example.project.saloon.gentlemanChair.service;

import com.example.project.saloon.gentlemanChair.entity.ClientEntity;
import com.example.project.saloon.gentlemanChair.entity.Roles;
import com.example.project.saloon.gentlemanChair.entity.User;
import com.example.project.saloon.gentlemanChair.payload.auth.SignupRequestDto;
import com.example.project.saloon.gentlemanChair.repository.ClientRepository;
import com.example.project.saloon.gentlemanChair.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;

    public String newAccount(@Valid SignupRequestDto requestDto) {

        userRepository.findByEmail(requestDto.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("User Email Repeated");
                });


        User newUser = new User();
        newUser.setPassword(requestDto.getPassword());
        newUser.setEmail(requestDto.getEmail());
        newUser.setPhNumber(requestDto.getPhNumber());
        newUser.setUsername(requestDto.getUsername());
        newUser.setRole(Roles.CLIENT);
        newUser.setRequiredPasswordChange(false);


        clientRepository.save(ClientEntity
                .builder()
                .user(newUser)
                .preferBarber(null)
                .currentAppointment(null)
                .completedAppointments(0)
                .build()
        );

        return "Success";
    }
}
