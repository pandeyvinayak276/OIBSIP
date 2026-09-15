package com.oibsip.library.controller;

import com.oibsip.library.model.Book;
import com.oibsip.library.model.user;
import com.oibsip.library.service.BookService;
import com.oibsip.library.service.IssueService;
import com.oibsip.library.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user/books")
public class LibraryController {

    private final BookService bookService;
    private final IssueService issueService;
    private final ReservationService reservationService;

    public LibraryController(BookService bookService,
                             IssueService issueService,
                             ReservationService reservationService) {
        this.bookService = bookService;
        this.issueService = issueService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public String books(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            HttpSession session,
            Model model) {

        user user = (user) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"USER".equals(user.getRole())) {
            return "redirect:/admin/dashboard";
        }

        List<Book> books;

        if (keyword != null && !keyword.isBlank()) {
            books = bookService.searchBooks(keyword);
        } else if (category != null && !category.isBlank()) {
            books = bookService.getBooksByCategory(category);
        } else {
            books = bookService.getAllBooks();
        }

        List<String> categories = bookService.getAllBooks()
                .stream()
                .map(Book::getCategory)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        model.addAttribute("books", books);
        model.addAttribute("categories", categories);
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("user", user);

        return "user/books";
    }

    @PostMapping("/issue/{bookId}")
    public String issueBook(@PathVariable Long bookId,
                            HttpSession session) {

        user user = (user) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"USER".equals(user.getRole())) {
            return "redirect:/admin/dashboard";
        }

        issueService.issueBook(user, bookId);

        return "redirect:/user/books";
    }
    @GetMapping("/my-books")
    public String myBooks(HttpSession session, Model model) {

        user user = (user) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"USER".equals(user.getRole())) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("user", user);
        model.addAttribute("issues", issueService.getUserIssues(user));

        return "user/my-books";
    }
    @PostMapping("/return/{issueId}")
    public String returnBook(@PathVariable Long issueId,
                             HttpSession session) {

        user user = (user) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"USER".equals(user.getRole())) {
            return "redirect:/admin/dashboard";
        }

        issueService.returnBook(user, issueId);

        return "redirect:/user/books/my-books";
    }
    @PostMapping("/reserve/{bookId}")
    public String reserveBook(@PathVariable Long bookId,
                              HttpSession session) {

        user user = (user) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"USER".equals(user.getRole())) {
            return "redirect:/admin/dashboard";
        }

        reservationService.reserveBook(user, bookId);

        return "redirect:/user/books";
    }
    @GetMapping("/reservations")
    public String reservations(HttpSession session, Model model) {

        user user = (user) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"USER".equals(user.getRole())) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("user", user);
        model.addAttribute(
                "reservations",
                reservationService.getUserReservations(user)
        );

        return "user/reservations";
    }
}