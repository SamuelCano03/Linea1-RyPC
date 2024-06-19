package com.tren.linea1_service.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tren.linea1_service.dto.AuthRequestDTO;
import com.tren.linea1_service.dto.AuthResponseDTO;
import com.tren.linea1_service.dto.RegisterRequestDTO;
import com.tren.linea1_service.dto.UserProfileDTO;
import com.tren.linea1_service.service.AuthService;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<UserProfileDTO> register(@Validated @RequestBody RegisterRequestDTO request) {
    UserProfileDTO responseDTO = authService.register(request);
    return new ResponseEntity<>(responseDTO, HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> authenticate(@Validated @RequestBody AuthRequestDTO request) {
    AuthResponseDTO responseDTO = authService.authenticate(request);
    return new ResponseEntity<>(responseDTO, HttpStatus.OK);
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    authService.refreshToken(request, response);
  }


}