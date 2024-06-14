package com.tren.linea1_service.repository;

import com.tren.linea1_service.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmail(String email);
    Optional<User> findoneByDni(String dni);
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);
}