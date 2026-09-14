package com.oibsip.library.repository;

import com.oibsip.library.model.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<user, Long> {

    Optional<user> findByUsername(String username);

    boolean existsByUsername(String username);
}
