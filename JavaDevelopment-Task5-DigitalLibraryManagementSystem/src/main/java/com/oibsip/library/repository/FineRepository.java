package com.oibsip.library.repository;

import com.oibsip.library.model.Fine;
import com.oibsip.library.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FineRepository extends JpaRepository<Fine, Long> {

    Optional<Fine> findByIssue(Issue issue);

    List<Fine> findByStatus(String status);
}
