package com.tren.linea1_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tren.linea1_service.dto.ChangePassRequestDTO;
import com.tren.linea1_service.dto.UserProfileDTO;
import com.tren.linea1_service.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getUserProfile() {
        UserProfileDTO profile = userService.getUserProfile();
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Validated @RequestBody ChangePassRequestDTO changePassRequestDTO) {
        userService.changePassword(changePassRequestDTO);
        return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
    }
    
}
