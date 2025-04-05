package com.example.repository;


import com.example.domain.user.User;
import liquibase.logging.mdc.customobjects.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
