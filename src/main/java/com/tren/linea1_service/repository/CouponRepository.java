package com.tren.linea1_service.repository;

import com.tren.linea1_service.model.entity.Coupon;
import com.tren.linea1_service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query(value = "SELECT * FROM Coupon c WHERE c.user_id = :userId AND c.is_active = true", nativeQuery = true)
    List<Coupon> findActiveCouponsByUserId(Long userId);
}
