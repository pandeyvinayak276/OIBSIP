package com.oibsip.library.service;

import com.oibsip.library.model.Book;
import com.oibsip.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public List<Book> searchBooks(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return getAllBooks();
        }

        return bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
                        keyword.trim(),
                        keyword.trim()
                );
    }

    public List<Book> getBooksByCategory(String category) {

        if (category == null || category.isBlank()) {
            return getAllBooks();
        }

        return bookRepository.findByCategoryIgnoreCase(category.trim());
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public boolean isbnExists(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }
}