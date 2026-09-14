package com.oibsip.library.controller;

import com.oibsip.library.model.user;
import com.oibsip.library.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String fullName,
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        if (fullName.isBlank() || username.isBlank() || password.isBlank()) {
            model.addAttribute("error", "All fields are required.");
            return "register";
        }

        boolean registered = authService.registerUser(
                fullName.trim(),
                username.trim(),
                password
        );

        if (!registered) {
            model.addAttribute("error", "Username already exists.");
            return "register";
        }

        model.addAttribute("success", "Registration successful. Please login.");
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        user user = authService.authenticate(username.trim(), password);

        if (user == null) {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }

        session.setAttribute("loggedInUser", user);

        if ("ADMIN".equals(user.getRole())) {
            return "redirect:/admin/dashboard";
        }

        return "redirect:/user/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
