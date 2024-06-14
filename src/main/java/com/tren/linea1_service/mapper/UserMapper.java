package com.tren.linea1_service.mapper;

import com.tren.linea1_service.model.dto.SignupFormDTO;
import com.tren.linea1_service.model.dto.UserProfileDTO;
import com.tren.linea1_service.model.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
@AllArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public User convertToEntity(SignupFormDTO signupFormDTO){
        return  modelMapper.map(signupFormDTO, User.class);
    }

    public UserProfileDTO convertToDTO(User user){
        return  modelMapper.map(user, UserProfileDTO.class);
    }

}
