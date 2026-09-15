package com.oibsip.library.service;

import com.oibsip.library.model.Fine;
import com.oibsip.library.model.Issue;
import com.oibsip.library.repository.FineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FineService {

    private static final double FINE_PER_DAY = 5.0;

    private final FineRepository fineRepository;

    public FineService(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    public Fine calculateFine(Issue issue) {

        LocalDate endDate = issue.getReturnDate() != null
                ? issue.getReturnDate()
                : LocalDate.now();

        if (!endDate.isAfter(issue.getDueDate())) {
            return null;
        }

        long lateDays = ChronoUnit.DAYS.between(
                issue.getDueDate(),
                endDate
        );

        double amount = lateDays * FINE_PER_DAY;

        Fine fine = fineRepository.findByIssue(issue)
                .orElse(new Fine());

        fine.setIssue(issue);
        fine.setAmount(amount);

        if (fine.getStatus() == null) {
            fine.setStatus("UNPAID");
        }

        return fineRepository.save(fine);
    }

    public List<Fine> getAllFines() {
        return fineRepository.findAll();
    }

    public List<Fine> getUnpaidFines() {
        return fineRepository.findByStatus("UNPAID");
    }

    public void markAsPaid(Long fineId) {
        fineRepository.findById(fineId).ifPresent(fine -> {
            fine.setStatus("PAID");
            fineRepository.save(fine);
        });
    }
}