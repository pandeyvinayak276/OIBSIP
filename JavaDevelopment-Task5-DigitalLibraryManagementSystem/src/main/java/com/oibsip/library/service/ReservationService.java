package com.oibsip.library.service;

import com.oibsip.library.model.Book;
import com.oibsip.library.model.Reservation;
import com.oibsip.library.model.user;
import com.oibsip.library.repository.BookRepository;
import com.oibsip.library.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              BookRepository bookRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
    }

    public boolean reserveBook(user user, Long bookId) {

        Book book = bookRepository.findById(bookId).orElse(null);

        if (book == null || book.getAvailableQuantity() > 0) {
            return false;
        }

        List<Reservation> existingReservations =
                reservationRepository.findByUserAndStatus(user, "WAITING");

        boolean alreadyReserved = existingReservations.stream()
                .anyMatch(reservation ->
                        reservation.getBook().getId().equals(bookId));

        if (alreadyReserved) {
            return false;
        }

        Reservation reservation = new Reservation(
                user,
                book,
                LocalDate.now()
        );

        reservationRepository.save(reservation);

        return true;
    }

    public List<Reservation> getUserReservations(user user) {
        return reservationRepository.findByUser(user);
    }
}