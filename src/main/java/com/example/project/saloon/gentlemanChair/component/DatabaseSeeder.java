package com.example.project.saloon.gentlemanChair.component;

import com.example.project.saloon.gentlemanChair.entity.Roles;
import com.example.project.saloon.gentlemanChair.entity.User;
import com.example.project.saloon.gentlemanChair.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String addEmail = "admin@gentleman.com";

        if (!userRepository.findByEmail(addEmail).isEmpty()) {
            System.out.println("Admin already Exists");
            return;
        }

        userRepository.save(User
                .builder()
                .role(Roles.ADMIN)
                .email(addEmail)
                .phNumber(123456789L)
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .build());

        System.out.println("Admin created Successfully !");

    }
}
