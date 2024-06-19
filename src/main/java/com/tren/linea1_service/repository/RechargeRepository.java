package com.tren.linea1_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tren.linea1_service.model.Recharge;
import java.util.List;

public interface RechargeRepository extends JpaRepository<Recharge, Long>{
    Optional<Recharge> findByVoucherNumber(String voucherNumber);

    @Query(value = "SELECT r FROM Recharge r WHERE r.card.cardNumber = :cardNumber")
    Optional<List<Recharge>> findByCardNumber(String cardNumber);

    @Query(value = "SELECT r FROM Recharge r WHERE r.card.user.email = :email")
    Optional<List<Recharge>> findByUserEmail(String email);

}
