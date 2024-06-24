package com.tren.linea1_service.controller;

import com.tren.linea1_service.dto.CouponCreateRequestDTO;
import com.tren.linea1_service.dto.CouponResponseDTO;
import com.tren.linea1_service.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/coupon")

public class CouponController {
    private final CouponService couponService;

    @PostMapping("/admin/create")
    public ResponseEntity<List<CouponResponseDTO>> createCoupon(@Validated @RequestBody CouponCreateRequestDTO couponCreateRequestDTO){
        List<CouponResponseDTO> cupon = couponService.createCoupons(couponCreateRequestDTO);
        return new ResponseEntity<>(cupon, HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<String> deleteCoupons(@RequestParam String code){
        couponService.deleteCoupons(code);
        return new ResponseEntity<>("Coupons deleted", HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<CouponResponseDTO> addCoupon(@RequestParam String code){
        CouponResponseDTO cupon = couponService.addCoupon(code);
        return new ResponseEntity<>(cupon, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CouponResponseDTO>> getAllCoupons(){
        List<CouponResponseDTO> coupons = couponService.getAllCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @PutMapping("/activate")
    public ResponseEntity<CouponResponseDTO> activateCoupon(@RequestParam String code){
        CouponResponseDTO cupon = couponService.activateCoupon(code);
        return new ResponseEntity<>(cupon, HttpStatus.OK);
    }
}
