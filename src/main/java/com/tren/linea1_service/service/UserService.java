package com.tren.linea1_service.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tren.linea1_service.dto.ChangePassRequestDTO;
import com.tren.linea1_service.dto.UserProfileDTO;
import com.tren.linea1_service.exceptions.BadRequestException;
import com.tren.linea1_service.exceptions.ResourceNotFoundException;
import com.tren.linea1_service.mapper.UserMapper;
import com.tren.linea1_service.model.User;
import com.tren.linea1_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional(readOnly = true)
    public UserProfileDTO getUserProfile() {
        User user = getUserByAuth();
        return userMapper.convertToDTO(user);
    }

    @Transactional
    public void changePassword(ChangePassRequestDTO request) {
        User user = getUserByAuth();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid password");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public User getUserByAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findUserByEmail(userDetails.getUsername())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
