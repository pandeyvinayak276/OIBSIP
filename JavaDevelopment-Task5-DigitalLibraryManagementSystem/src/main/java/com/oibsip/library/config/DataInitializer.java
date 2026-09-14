package com.oibsip.library.config;

import com.oibsip.library.model.user;
import com.oibsip.library.repository.UserRepository;
import com.oibsip.library.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeAdmin(
            UserRepository userRepository,
            AuthService authService) {

        return args -> {

            if (!userRepository.existsByUsername("admin")) {

                user admin = new user(
                        "Library Administrator",
                        "admin",
                        authService.hashPasswordForInitialization("admin123"),
                        "ADMIN"
                );

                userRepository.save(admin);

                System.out.println("Default admin account created.");
            }
        };
    }
}