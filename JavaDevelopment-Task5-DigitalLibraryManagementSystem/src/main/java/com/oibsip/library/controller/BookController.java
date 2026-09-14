package com.oibsip.library.controller;

import com.oibsip.library.model.Book;
import com.oibsip.library.model.user;
import com.oibsip.library.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String books(HttpSession session, Model model) {

        user user = (user) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equals(user.getRole())) {
            return "redirect:/user/dashboard";
        }

        model.addAttribute("books", bookService.getAllBooks());

        return "admin/books";
    }

    @GetMapping("/add")
    public String addBookPage(HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        return "admin/add-book";
    }

    @PostMapping("/add")
    public String addBook(
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String isbn,
            @RequestParam String category,
            @RequestParam int totalQuantity,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        if (title.isBlank()
                || author.isBlank()
                || isbn.isBlank()
                || category.isBlank()
                || totalQuantity <= 0) {

            model.addAttribute("error", "Please enter valid book details.");
            return "admin/add-book";
        }

        if (bookService.isbnExists(isbn.trim())) {
            model.addAttribute("error", "A book with this ISBN already exists.");
            return "admin/add-book";
        }

        Book book = new Book(
                title.trim(),
                author.trim(),
                isbn.trim(),
                category.trim(),
                totalQuantity
        );

        bookService.addBook(book);

        return "redirect:/admin/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookPage(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Book book = bookService.getBookById(id).orElse(null);

        if (book == null) {
            return "redirect:/admin/books";
        }

        model.addAttribute("book", book);

        return "admin/edit-book";
    }

    @PostMapping("/edit/{id}")
    public String editBook(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String isbn,
            @RequestParam String category,
            @RequestParam int totalQuantity,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Book book = bookService.getBookById(id).orElse(null);

        if (book == null) {
            return "redirect:/admin/books";
        }

        if (title.isBlank()
                || author.isBlank()
                || isbn.isBlank()
                || category.isBlank()
                || totalQuantity <= 0) {

            model.addAttribute("error", "Please enter valid book details.");
            model.addAttribute("book", book);
            return "admin/edit-book";
        }

        book.setTitle(title.trim());
        book.setAuthor(author.trim());
        book.setIsbn(isbn.trim());
        book.setCategory(category.trim());

        int issuedQuantity =
                book.getTotalQuantity() - book.getAvailableQuantity();

        if (totalQuantity < issuedQuantity) {
            model.addAttribute(
                    "error",
                    "Total quantity cannot be less than currently issued books."
            );
            model.addAttribute("book", book);
            return "admin/edit-book";
        }

        book.setTotalQuantity(totalQuantity);
        book.setAvailableQuantity(totalQuantity - issuedQuantity);

        bookService.updateBook(book);

        return "redirect:/admin/books";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(
            @PathVariable Long id,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        bookService.deleteBook(id);

        return "redirect:/admin/books";
    }

    private boolean isAdmin(HttpSession session) {

        user user = (user) session.getAttribute("loggedInUser");

        return user != null && "ADMIN".equals(user.getRole());
    }
}