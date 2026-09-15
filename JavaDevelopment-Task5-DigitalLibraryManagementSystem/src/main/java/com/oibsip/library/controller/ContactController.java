package com.oibsip.library.controller;

import com.oibsip.library.model.user;
import com.oibsip.library.service.ContactService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public String contactPage(HttpSession session, Model model) {

        user user = (user) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"USER".equals(user.getRole())) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("user", user);
        model.addAttribute("messages", contactService.getUserMessages(user));

        return "user/contact";
    }

    @PostMapping
    public String submitMessage(
            @RequestParam String subject,
            @RequestParam String message,
            HttpSession session,
            Model model) {

        user user = (user) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"USER".equals(user.getRole())) {
            return "redirect:/admin/dashboard";
        }

        if (subject.isBlank() || message.isBlank()) {
            model.addAttribute("error", "Subject and message are required.");
            model.addAttribute("user", user);
            model.addAttribute("messages", contactService.getUserMessages(user));
            return "user/contact";
        }

        contactService.saveMessage(user, subject, message);

        model.addAttribute("success",
                "Your query has been submitted successfully.");

        model.addAttribute("user", user);
        model.addAttribute("messages", contactService.getUserMessages(user));

        return "user/contact";
    }
}