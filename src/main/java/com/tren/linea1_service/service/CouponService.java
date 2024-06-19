package com.tren.linea1_service.service;

import com.tren.linea1_service.dto.CouponRequestDTO;
import com.tren.linea1_service.dto.CouponResponseDTO;
import com.tren.linea1_service.mapper.CouponMapper;
import com.tren.linea1_service.model.Coupon;
import com.tren.linea1_service.model.User;
import com.tren.linea1_service.repository.CouponRepository;
import com.tren.linea1_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> user = userRepository.findUserByEmail(userDetails.getUsername());
        Coupon coupon = couponMapper.convertToEntity(couponRequestDTO);
        coupon.setUser(user.get());
        coupon.setActive(true);
        return couponRepository.save(coupon);
    }

    public List<CouponResponseDTO> getCoupons(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> user = userRepository.findUserByEmail(userDetails.getUsername());
        List<Coupon> coupons = couponRepository.findActiveCouponsByUserId(user.get().getId());
        return couponMapper.convertToDtoList(coupons);
    }
}
