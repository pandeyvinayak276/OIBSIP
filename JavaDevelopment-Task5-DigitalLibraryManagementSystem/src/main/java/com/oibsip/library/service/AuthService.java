package com.oibsip.library.service;

import com.oibsip.library.model.user;
import com.oibsip.library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(String fullName, String username, String password) {

        if (userRepository.existsByUsername(username)) {
            return false;
        }

        user user = new user(
                fullName,
                username,
                hashPassword(password),
                "USER"
        );

        userRepository.save(user);
        return true;
    }

    public user authenticate(String username, String password) {

        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(hashPassword(password)))
                .orElse(null);
    }

    private String hashPassword(String password) {
        

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8)
            );

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Password hashing algorithm unavailable", e);
        }
    }
    public String hashPasswordForInitialization(String password) {
        return hashPassword(password);
    }
}
