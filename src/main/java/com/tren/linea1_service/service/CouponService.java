package com.tren.linea1_service.service;

import com.tren.linea1_service.dto.CouponCreateRequestDTO;
import com.tren.linea1_service.dto.CouponResponseDTO;
import com.tren.linea1_service.exceptions.BadRequestException;
import com.tren.linea1_service.exceptions.ResourceNotFoundException;
import com.tren.linea1_service.mapper.CouponMapper;
import com.tren.linea1_service.model.Coupon;
import com.tren.linea1_service.model.User;
import com.tren.linea1_service.repository.CouponRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;
    private final UserService userService;

    @Transactional
    public List<CouponResponseDTO> createCoupons(CouponCreateRequestDTO couponCreateRequestDTO){
        List<Coupon> coupons = new ArrayList<>();
        for (int i = 0; i < couponCreateRequestDTO.getQuantity(); i++) {
            Coupon coupon = couponMapper.convertToEntity(couponCreateRequestDTO);
            coupon.setActive(false);
            coupon.setExpirationDate(LocalDate.parse(couponCreateRequestDTO.getExpirationDate()));
            coupon.setStartDate(LocalDate.parse(couponCreateRequestDTO.getStartDate()));
            coupon.setDiscountValue(BigDecimal.valueOf(couponCreateRequestDTO.getDiscountValue()));
            coupon.setMinAmount(BigDecimal.valueOf(couponCreateRequestDTO.getMinAmount()));
            coupons.add(coupon);
        }
        List<Coupon> savedCoupons = couponRepository.saveAll(coupons);
        return couponMapper.convertToDTOList(savedCoupons);
    }

    @Transactional
    public void deleteCoupons(String code){
        boolean couponExists = couponRepository.existsByCode(code);
        if (!couponExists) {
            throw new ResourceNotFoundException("Coupons not found");
        }
        couponRepository.deleteByCode(code);
    }

    @Transactional
    public CouponResponseDTO addCoupon(String code){
        User user = userService.getUserByAuth();
        boolean couponExists = couponRepository.existsByCodeAndUserEmail(code, user.getEmail());
        if (couponExists) {
            throw new BadRequestException("Coupon already added");
        }
        List<Coupon> coupons = couponRepository.findByCodeAndUserIsNull(code)
                    .orElseThrow(()-> new ResourceNotFoundException("Coupon not found"));
        Coupon coupon = coupons.get(0);
        coupon.setUser(user);
        couponRepository.save(coupon);
        return couponMapper.convertToDTO(coupon);
    }

    @Transactional(readOnly = true)
    public List<CouponResponseDTO> getAllCoupons(){
        User user = userService.getUserByAuth();
        List<Coupon> coupons = couponRepository.findCouponsByUserEmail(user.getEmail())
                    .orElseThrow(()-> new ResourceNotFoundException("This user has no coupons"));
        return couponMapper.convertToDTOList(coupons);
    }

    @Transactional
    public CouponResponseDTO activateCoupon(String code){
        User user = userService.getUserByAuth();
        Coupon coupon = couponRepository.findByCodeAndUserEmail(code, user.getEmail())
                    .orElseThrow(()-> new ResourceNotFoundException("Coupon not found"));
        if (coupon.isActive()) {
            throw new BadRequestException("Coupon already activated");
        }
        if (coupon.getExpirationDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Coupon expired");
        }
        if (coupon.getMaxUsageCount() == 0) {
            throw new BadRequestException("Coupon has reached its maximum usage count");
        }
        couponRepository.deactivateAllActiveCouponsForUser(user.getEmail());
        coupon.setActive(true);
        couponRepository.save(coupon);
        return couponMapper.convertToDTO(coupon);
    }
}
