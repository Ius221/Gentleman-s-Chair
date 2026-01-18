package com.example.project.saloon.gentlemanChair.service;

import com.example.project.saloon.gentlemanChair.config.EmailResponse;
import com.example.project.saloon.gentlemanChair.entity.*;
import com.example.project.saloon.gentlemanChair.payload.auth.SignupRequestDto;
import com.example.project.saloon.gentlemanChair.payload.client.BookAppointmentRequestDto;
import com.example.project.saloon.gentlemanChair.payload.client.BookAppointmentResponseDto;
import com.example.project.saloon.gentlemanChair.repository.BarberRepository;
import com.example.project.saloon.gentlemanChair.repository.ClientRepository;
import com.example.project.saloon.gentlemanChair.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BarberRepository barberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;


    public String newAccount(@Valid SignupRequestDto requestDto) {

        userRepository.findByEmail(requestDto.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("User Email Repeated");
                });

        ClientEntity client = ClientEntity
                .builder()
                .preferBarber(null)
                .currentAppointmentEntity(null)
                .completedAppointments(0)
                .build();

        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        newUser.setEmail(requestDto.getEmail());
        newUser.setPhNumber(requestDto.getPhNumber());
        newUser.setUsername(requestDto.getUsername());
        newUser.setClientEntity(client);
        newUser.setRole(Roles.CLIENT);
        newUser.setRequiredPasswordChange(false);

        client.setUser(newUser);

        userRepository.save(newUser);

        return "Success";
    }

    public BookAppointmentResponseDto bookAppointment(@Valid BookAppointmentRequestDto requestDto) {

        User currUser = userRepository.findByEmail(requestDto.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("Client Email Not Found"));
        ClientEntity client = currUser.getClientEntity();

        currUser = userRepository.findByEmail(requestDto.getBarberEmail())
                .orElseThrow(() -> new IllegalArgumentException("Barber Email Not Found"));
        BarberEntity barber = currUser.getBarberEntity();

        Map<String, Map<String, Integer>> allServices = ServiceWithPrice.getServiceAndPrices();

        Map<String, Integer> servicePrice = new HashMap<>();

        Set<String> requestedServices = new HashSet<>(requestDto.getServices());

        allServices.values().forEach(serviceMap -> {
            serviceMap.forEach((service, price) -> {
                if (requestedServices.contains(service)) {
                    servicePrice.put(service, price);
                }
            });
        });


        Integer totalMoney = servicePrice
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        emailService.sendSimpleEmail(
                barber.getUser().getEmail(),
                "New Client Booking Request – Action Required",
                EmailResponse.getBarberMail(
                        barber.getUser().getUsername(),
                        requestDto.getTime(),
                        requestDto.getDate(),
                        requestedServices,
                        totalMoney
                )
        );

        return BookAppointmentResponseDto
                .builder()
                .appointmentStatus(Status.PENDING)
                .barberName(barber.getUser().getUsername())
                .barberExp(barber.getExperience().toString())
                .time(requestDto.getTime())
                .date(requestDto.getDate())
                .services(requestDto.getServices())
                .build();
    }
}
