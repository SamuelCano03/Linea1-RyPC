package com.tren.linea1_service.repository;

import com.tren.linea1_service.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query(value = "SELECT c FROM Coupon c WHERE c.user.email = :email")
    Optional<List<Coupon>> findCouponsByUserEmail(String email);
    
    @Query(value = "SELECT c FROM Coupon c WHERE c.code = :code AND c.user IS NULL")
    Optional<List<Coupon>> findByCodeAndUserIsNull(String code);

    @Query(value = "SELECT c FROM Coupon c WHERE c.code = :code AND c.user.email = :email")
    Optional<Coupon> findByCodeAndUserEmail(String code, String email);

    @Query(value = "SELECT c FROM Coupon c WHERE c.isActive = true AND c.user.email = :email")
    Optional<Coupon> findByActiveAndUserEmail(String email);
    
    @Query(value = "SELECT COUNT(c) > 0 FROM Coupon c WHERE c.code = :code AND c.user.email = :email")
    boolean existsByCodeAndUserEmail(String code, String email);

    boolean existsByCode(String code);

    @Query(value = "SELECT COUNT(c) > 0 FROM Coupon c WHERE c.user.email = :email AND c.isActive = true")
    boolean existsByActiveAndUserEmail(String email);

    @Modifying
    @Query("UPDATE Coupon c SET c.isActive = false WHERE c.user.email = :email AND c.isActive = true")
    void deactivateAllActiveCouponsForUser(String email);

    @Modifying
    @Query("DELETE FROM Coupon c WHERE c.code = :code")
    void deleteByCode(String code);
}
