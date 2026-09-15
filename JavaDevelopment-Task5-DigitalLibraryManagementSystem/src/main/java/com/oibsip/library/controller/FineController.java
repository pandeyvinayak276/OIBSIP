package com.oibsip.library.controller;

import com.oibsip.library.model.user;
import com.oibsip.library.service.FineService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/fines")
public class FineController {

    private final FineService fineService;

    public FineController(FineService fineService) {
        this.fineService = fineService;
    }

    @GetMapping
    public String fines(HttpSession session, Model model) {

        user user = (user) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equals(user.getRole())) {
            return "redirect:/user/dashboard";
        }

        model.addAttribute("fines", fineService.getAllFines());

        return "admin/fines";
    }

    @PostMapping("/pay/{id}")
    public String markAsPaid(@PathVariable Long id,
                             HttpSession session) {

        user user = (user) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equals(user.getRole())) {
            return "redirect:/user/dashboard";
        }

        fineService.markAsPaid(id);

        return "redirect:/admin/fines";
    }
}