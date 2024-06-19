package com.tren.linea1_service.controller;

import com.tren.linea1_service.model.dto.CouponRequestDTO;
import com.tren.linea1_service.model.dto.CouponResponseDTO;
import com.tren.linea1_service.model.entity.Coupon;
import com.tren.linea1_service.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/coupons")

public class CouponController {
    private final CouponService couponService;

    @GetMapping("/all")
    public ResponseEntity<List<CouponResponseDTO>> getAllCoupons(){
        List<CouponResponseDTO> cup = couponService.getCoupons();
        return ResponseEntity.ok(cup);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public ResponseEntity<Coupon> addCoupon(@RequestBody CouponRequestDTO dto){
        Coupon cup = couponService.addCoupon(dto);
        return ResponseEntity.ok(cup);
    }
}
