package com.tren.linea1_service.service;

import com.tren.linea1_service.exception.BadRequestException;
import com.tren.linea1_service.exception.InvalidCredentialsException;
import com.tren.linea1_service.exception.ResourceNotFoundException;
import com.tren.linea1_service.mapper.UserMapper;
import com.tren.linea1_service.model.dto.AuthRequestDTO;
import com.tren.linea1_service.model.dto.SignupFormDTO;
import com.tren.linea1_service.model.dto.UserProfileDTO;
import com.tren.linea1_service.model.entities.User;
import com.tren.linea1_service.repository.UserRepository;
import com.tren.linea1_service.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;
    private TokenProvider tokenProvider;
    private AuthenticationManager authenticationManager;

    public UserProfileDTO signup(SignupFormDTO signupFormDTO) {
        boolean emailAlreadyExists =
                userRepository.existsByEmail(signupFormDTO.getEmail());
        if (emailAlreadyExists) {
            throw new BadRequestException("El email ya está siendo usado por otro usuario.");
        }
        boolean dniAlreadyExists =
                userRepository.existsByDni(signupFormDTO.getDni());
        if (dniAlreadyExists) {
            throw new BadRequestException("El dni ya está registrado en el sistema.");
        }
        User user = userMapper.convertToEntity(signupFormDTO);
        user.setName(signupFormDTO.getName());
        user.setLast_name(signupFormDTO.getLastName());
        user.setEmail(signupFormDTO.getEmail());
        user.setDni(signupFormDTO.getDni());
        user.setPassword(passwordEncoder.encode(signupFormDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return userMapper.convertToDTO(user);
    }

    public UserProfileDTO validateSignIn(AuthRequestDTO authRequestDTO) {
        User user = userRepository.findOneByEmail(authRequestDTO.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Correo o contraseña invalidos"));

        if (user == null || !passwordEncoder.matches(authRequestDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Correo o contraseña invalidos");
        }
        return userMapper.convertToDTO(user);
    }

    public UserProfileDTO findByEmail(String email) {
        User user = userRepository
                .findOneByEmail(email)
                .orElseThrow(ResourceNotFoundException::new);
        return userMapper.convertToDTO(user);
    }
}