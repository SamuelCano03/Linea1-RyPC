package com.tren.linea1_service.mapper;

import com.tren.linea1_service.dto.CouponRequestDTO;
import com.tren.linea1_service.dto.CouponResponseDTO;
import com.tren.linea1_service.model.Coupon;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Data

public class CouponMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public Coupon convertToEntity(CouponRequestDTO dto){
        return modelMapper.map(dto, Coupon.class);
    }

    public CouponResponseDTO convertToDTO(Coupon cup){
        return  modelMapper.map(cup, CouponResponseDTO.class);
    }

    public List<CouponResponseDTO>convertToDtoList(List<Coupon> cup){
        return cup.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
