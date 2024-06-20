package com.tren.linea1_service.mapper;

import com.tren.linea1_service.dto.CouponCreateRequestDTO;
import com.tren.linea1_service.dto.CouponResponseDTO;
import com.tren.linea1_service.model.Coupon;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data

public class CouponMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public Coupon convertToEntity(CouponCreateRequestDTO couponCreateRequestDTO){
        return modelMapper.map(couponCreateRequestDTO, Coupon.class);
    }

    public CouponResponseDTO convertToDTO(Coupon coupon){
        return  modelMapper.map(coupon, CouponResponseDTO.class);
    }

    public List<CouponResponseDTO>convertToDTOList(List<Coupon> coupons){
        return coupons.stream()
        .map(this::convertToDTO)
        .toList();
    }
}
