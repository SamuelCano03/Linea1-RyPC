package com.tren.linea1_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tren.linea1_service.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    void deleteByToken(String token);


    @Modifying
    @Query("DELETE FROM VerificationToken v WHERE v.user.email = :email")
    void deleteByUserEmail(String email);
}
