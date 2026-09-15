package com.oibsip.library.controller;

import com.oibsip.library.model.user;
import com.oibsip.library.service.IssueService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/issued-books")
public class AdminIssueController {

    private final IssueService issueService;

    public AdminIssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping
    public String issuedBooks(HttpSession session, Model model) {

        user user = (user) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equals(user.getRole())) {
            return "redirect:/user/dashboard";
        }

        model.addAttribute(
                "issues",
                issueService.getAllIssuedBooks()
        );

        return "admin/issued-books";
    }
}