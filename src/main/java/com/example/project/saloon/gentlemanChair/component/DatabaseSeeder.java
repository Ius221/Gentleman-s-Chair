package com.example.project.saloon.gentlemanChair.component;

import com.example.project.saloon.gentlemanChair.entity.BarberEntity;
import com.example.project.saloon.gentlemanChair.entity.Roles;
import com.example.project.saloon.gentlemanChair.entity.User;
import com.example.project.saloon.gentlemanChair.entity.WorkingDays;
import com.example.project.saloon.gentlemanChair.repository.BarberRepository;
import com.example.project.saloon.gentlemanChair.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BarberRepository barberRepository;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "gentleman@yopmail.com";
        String barberEmail = "gentlemanbarber@yopmail.com";

        if (!userRepository.findByEmail(adminEmail).isEmpty()) {
            System.out.println("Admin already Exists");
            return;
        }

        if (!userRepository.findByEmail(barberEmail).isEmpty()) {
            System.out.println("Barber already Exists");
            return;
        }

        try {
            userRepository.save(User
                    .builder()
                    .role(Roles.ADMIN)
                    .email(adminEmail)
                    .phNumber(123456789L)
                    .requiredPasswordChange(false)
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .build());

            System.out.println("Admin created Successfully !");
            System.out.println("Admin Email: " + adminEmail);
            System.out.println("Admin Password: admin");

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e);
        }

        try {
            userRepository.save(
                    User
                            .builder()
                            .username("Ramesh babu")
                            .email(barberEmail)
                            .phNumber(999333222L)
                            .password(passwordEncoder.encode("Ayush@123"))
                            .role(Roles.MANAGER)
                            .requiredPasswordChange(false)
                            .barberEntity(
                                    BarberEntity
                                            .builder()
                                            .experience(3)
                                            .specialization(Set.of("HairCutting", "Beard"))
                                            .bio("I am a good barber for haircutting and beard trimming")
                                            .isAvailable(true)
                                            .workingDays(Set.of(WorkingDays.MONDAY, WorkingDays.TUESDAY))
                                            .workingHour("12")
                                            .build()
                            )
                            .build()
            );
            System.out.println("Barber created Successfully !");
            System.out.println("Barber Email: " + barberEmail);
            System.out.println("Barber Password: Ayush@123");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
