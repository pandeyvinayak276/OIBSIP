package com.oibsip.library.service;

import com.oibsip.library.model.ContactMessage;
import com.oibsip.library.model.user;
import com.oibsip.library.repository.ContactMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactMessageRepository contactMessageRepository;

    public ContactService(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    public ContactMessage saveMessage(user user, String subject, String message) {

        ContactMessage contactMessage =
                new ContactMessage(user, subject.trim(), message.trim());

        return contactMessageRepository.save(contactMessage);
    }

    public List<ContactMessage> getUserMessages(user user) {
        return contactMessageRepository.findByUser(user);
    }

    public List<ContactMessage> getAllMessages() {
        return contactMessageRepository.findAllByOrderByCreatedAtDesc();
    }

    public void markAsResolved(Long id) {

        contactMessageRepository.findById(id).ifPresent(message -> {
            message.setStatus("RESOLVED");
            contactMessageRepository.save(message);
        });
    }
}