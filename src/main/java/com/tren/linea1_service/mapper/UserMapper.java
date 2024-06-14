package com.tren.linea1_service.mapper;

import com.tren.linea1_service.model.dto.SignupFormDTO;
import com.tren.linea1_service.model.dto.UserProfileDTO;
import com.tren.linea1_service.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

@Component
@RequiredArgsConstructor


public class UserMapper {

    private final ModelMapper modelMapper;

    public User convertToEntity(SignupFormDTO signupFormDTO){
        return modelMapper.map(signupFormDTO, User.class);
    }

    public UserProfileDTO convertToDTO(User user){
        return  modelMapper.map(user, UserProfileDTO.class);
    }
}
