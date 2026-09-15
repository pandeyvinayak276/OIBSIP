package com.oibsip.library.service;

import com.oibsip.library.model.Book;
import com.oibsip.library.model.Issue;
import com.oibsip.library.model.user;
import com.oibsip.library.repository.BookRepository;
import com.oibsip.library.repository.IssueRepository;
import com.oibsip.library.service.FineService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IssueService {

    private static final int ISSUE_PERIOD_DAYS = 14;

    private final IssueRepository issueRepository;
    private final BookRepository bookRepository;
    private final FineService fineService;

    public IssueService(IssueRepository issueRepository,
                        BookRepository bookRepository,
                        FineService fineService) {
        this.issueRepository = issueRepository;
        this.bookRepository = bookRepository;
        this.fineService = fineService;
    }

    public boolean issueBook(user user, Long bookId) {

        Book book = bookRepository.findById(bookId).orElse(null);

        if (book == null || book.getAvailableQuantity() <= 0) {
            return false;
        }

        List<Issue> activeIssues =
                issueRepository.findByUserAndStatus(user, "ISSUED");

        boolean alreadyIssued = activeIssues.stream()
                .anyMatch(issue -> issue.getBook().getId().equals(bookId));

        if (alreadyIssued) {
            return false;
        }

        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);

        Issue issue = new Issue(
                user,
                book,
                LocalDate.now(),
                LocalDate.now().plusDays(ISSUE_PERIOD_DAYS)
        );

        issueRepository.save(issue);

        return true;
    }

    public List<Issue> getUserIssues(user user) {
        return issueRepository.findByUser(user);
    }

    public boolean returnBook(user user, Long issueId) {

        Issue issue = issueRepository.findById(issueId).orElse(null);

        if (issue == null
                || !issue.getUser().getId().equals(user.getId())
                || !"ISSUED".equals(issue.getStatus())) {
            return false;
        }

        issue.setReturnDate(LocalDate.now());
        issue.setStatus("RETURNED");
        issueRepository.save(issue);
        fineService.calculateFine(issue);

        Book book = issue.getBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);

        bookRepository.save(book);
        issueRepository.save(issue);

        return true;
    }
}