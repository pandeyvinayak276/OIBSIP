package com.oibsip.library.repository;

import com.oibsip.library.model.ContactMessage;
import com.oibsip.library.model.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    List<ContactMessage> findByUser(user user);

    List<ContactMessage> findAllByOrderByCreatedAtDesc();

    List<ContactMessage> findByStatus(String status);
}
