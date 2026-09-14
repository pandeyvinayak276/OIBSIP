package com.oibsip.library.repository;

import com.oibsip.library.model.Reservation;
import com.oibsip.library.model.user;
import com.oibsip.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser(user user);

    List<Reservation> findByBook(Book book);

    List<Reservation> findByUserAndStatus(user user, String status);

    List<Reservation> findByBookAndStatus(Book book, String status);
}
