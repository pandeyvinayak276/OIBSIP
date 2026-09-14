package com.oibsip.library.repository;

import com.oibsip.library.model.Issue;
import com.oibsip.library.model.user;
import com.oibsip.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByUser(user user);

    List<Issue> findByBook(Book book);

    List<Issue> findByStatus(String status);

    List<Issue> findByUserAndStatus(user user, String status);
}
