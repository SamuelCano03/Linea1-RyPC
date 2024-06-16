package com.tren.linea1_service.service;

import com.tren.linea1_service.exception.BadRequestException;
import com.tren.linea1_service.exception.InvalidCredentialsException;
import com.tren.linea1_service.exception.ResourceNotFoundException;
import com.tren.linea1_service.mapper.UserMapper;
import com.tren.linea1_service.model.dto.AuthRequestDTO;
import com.tren.linea1_service.model.dto.SignupFormDTO;
import com.tren.linea1_service.model.dto.UserProfileDTO;
import com.tren.linea1_service.model.entity.User;
import com.tren.linea1_service.repository.UserRepository;
import com.tren.linea1_service.security.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserProfileDTO signup(SignupFormDTO signupFormDTO, String dni) {
        boolean emailAlreadyExists =
                userRepository.existsByEmail(signupFormDTO.getEmail());
        if (emailAlreadyExists) {
            throw new BadRequestException("El email ya está siendo usado por otro usuario.");
        }
        User user = userMapper.convertToEntity(signupFormDTO);
        user.setName(signupFormDTO.getName());
        user.setLast_name(signupFormDTO.getLastName());
        user.setEmail(signupFormDTO.getEmail());
        user.setDni(signupFormDTO.getDni());
        user.setDniImage(dni);
        user.setPassword(passwordEncoder.encode(signupFormDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return userMapper.convertToDTO(user);
    }

    public UserProfileDTO validateSignIn(AuthRequestDTO authRequestDTO) {
        User user = userRepository.findByEmail(authRequestDTO.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Correo o contraseña invalidos"));

        if (user == null || !passwordEncoder.matches(authRequestDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Correo o contraseña invalidos");
        }
        return userMapper.convertToDTO(user);
    }

    public UserProfileDTO findByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(ResourceNotFoundException::new);
        return userMapper.convertToDTO(user);
    }

    public UserProfileDTO updatePersonalInfo(UserProfileDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName()).orElseThrow(ResourceNotFoundException::new);
        Field[] fields = dto.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            try {
                Object value = field.get(dto);
                if(value != null){
                    Field perInfo = user.getClass().getDeclaredField(field.getName());
                    perInfo.setAccessible(true);
                    ReflectionUtils.setField(perInfo,user,value);
                    perInfo.setAccessible(false);
                }
                field.setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        userRepository.save(user);
        return userMapper.convertToDTO(user);
    }

    public String verify(String path) {
        User user = userRepository.findByEmail("email@email.com").orElseThrow(ResourceNotFoundException::new);
        user.setDniImage(path);
        user.setVerified(true);
        userRepository.save(user);
        return "Usuario verificado";
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }
}