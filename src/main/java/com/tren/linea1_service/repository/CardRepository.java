package com.tren.linea1_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tren.linea1_service.model.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long>{
    
    boolean existsByCardNumber(String cardNumber);

    Optional<Card> findByCardNumber(String cardNumber);

    @Query(value = "SELECT c FROM Card c WHERE c.cardNumber = :cardNumber AND c.user.email = :email")
    Optional<Card> findByCardNumberAndUserEmail(String cardNumber, String email);

    Optional<List<Card>> findByUserEmail(String email);

    int countByUserEmail(String email);
} 