package com.tren.linea1_service.controller;

import com.tren.linea1_service.model.dto.AuthRequestDTO;
import com.tren.linea1_service.model.dto.AuthResponseDTO;
import com.tren.linea1_service.model.dto.SignupFormDTO;
import com.tren.linea1_service.model.dto.UserProfileDTO;
import com.tren.linea1_service.security.TokenProvider;
import com.tren.linea1_service.service.StorageService;
import com.tren.linea1_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final StorageService storageService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public UserProfileDTO signup(@RequestBody @Validated SignupFormDTO signupFormDTO) {
        return userService.signup(signupFormDTO);
    }


    public

}
