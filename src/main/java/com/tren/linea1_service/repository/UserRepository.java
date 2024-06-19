package com.tren.linea1_service.repository;

import com.tren.linea1_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByDni(String dni);
    
}