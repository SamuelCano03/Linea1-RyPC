package com.tren.linea1_service.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.tren.linea1_service.dto.RechargeResponseDTO;
import com.tren.linea1_service.model.Recharge;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RechargeMapper {
     private final ModelMapper modelMapper;

    public RechargeResponseDTO convertToDTO(Recharge recharge){
        return  modelMapper.map(recharge, RechargeResponseDTO.class);
    }

    public List<RechargeResponseDTO> convertToDTOList(List<Recharge> recharges){
        return recharges.stream()
        .map(this::convertToDTO)
        .toList();
    }
    
}
