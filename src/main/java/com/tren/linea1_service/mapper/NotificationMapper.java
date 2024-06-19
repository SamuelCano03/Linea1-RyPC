package com.tren.linea1_service.mapper;

import com.tren.linea1_service.model.dto.NotificationRequestDTO;
import com.tren.linea1_service.model.dto.NotificationResponseDTO;
import com.tren.linea1_service.model.dto.SignupFormDTO;
import com.tren.linea1_service.model.dto.UserProfileDTO;
import com.tren.linea1_service.model.entity.Notification;
import com.tren.linea1_service.model.entity.User;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Data

public class NotificationMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public Notification convertToEntity(NotificationRequestDTO dto){
        return modelMapper.map(dto, Notification.class);
    }

    public NotificationResponseDTO convertToDTO(Notification not){
        return  modelMapper.map(not, NotificationResponseDTO.class);
    }

    public List<NotificationResponseDTO> convertToDtoList(List<Notification> not){
        return not.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
