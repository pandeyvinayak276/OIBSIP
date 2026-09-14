package com.oibsip.library.controller;

import com.oibsip.library.model.user;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {

        user user = (user) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equals(user.getRole())) {
            return "redirect:/user/dashboard";
        }

        model.addAttribute("user", user);

        return "admin/dashboard";
    }
}