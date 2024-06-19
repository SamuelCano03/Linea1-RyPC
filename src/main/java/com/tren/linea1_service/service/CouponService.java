package com.tren.linea1_service.service;

import com.tren.linea1_service.exception.ResourceNotFoundException;
import com.tren.linea1_service.mapper.CouponMapper;
import com.tren.linea1_service.model.dto.CouponRequestDTO;
import com.tren.linea1_service.model.dto.CouponResponseDTO;
import com.tren.linea1_service.model.entity.Coupon;
import com.tren.linea1_service.model.entity.User;
import com.tren.linea1_service.repository.CouponRepository;
import com.tren.linea1_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CouponService {
    private final UserRepository userRepository;
    CouponRepository couponRepository;
    CouponMapper couponMapper;

    public Coupon addCoupon(CouponRequestDTO couponRequestDTO){
        if(SecurityContextHolder.getContext().getAuthentication() == null){
            throw new ResourceNotFoundException("Not logged in");
        }
        Optional<User> user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Coupon coupon = couponMapper.convertToEntity(couponRequestDTO);
        coupon.setUser(user.get());
        coupon.setActive(true);
        return couponRepository.save(coupon);
    }

    public List<CouponResponseDTO> getCoupons(){
        if(SecurityContextHolder.getContext().getAuthentication() == null){
            throw new ResourceNotFoundException("Not logged in");
        }
        Optional<User> user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Coupon> coupons = couponRepository.findActiveCouponsByUserId(user.get().getId());
        return couponMapper.convertToDtoList(coupons);
    }
}
