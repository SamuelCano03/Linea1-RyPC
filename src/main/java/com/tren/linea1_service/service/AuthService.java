package com.tren.linea1_service.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tren.linea1_service.dto.AuthRequestDTO;
import com.tren.linea1_service.dto.AuthResponseDTO;
import com.tren.linea1_service.dto.RegisterRequestDTO;
import com.tren.linea1_service.dto.ResetPassRequestDTO;
import com.tren.linea1_service.dto.UserProfileDTO;
import com.tren.linea1_service.dto.VerificationRequestDTO;
import com.tren.linea1_service.exceptions.BadRequestException;
import com.tren.linea1_service.mapper.UserMapper;
import com.tren.linea1_service.repository.TokenRepository;
import com.tren.linea1_service.repository.UserRepository;
import com.tren.linea1_service.security.JwtService;
import org.springframework.http.HttpHeaders;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.tren.linea1_service.model.Token;
import com.tren.linea1_service.model.User;
import com.tren.linea1_service.model.VerificationToken;
import com.tren.linea1_service.model.enums.Role;
import com.tren.linea1_service.model.enums.TokenType;
import java.util.Map;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final VerificationTokenService verificationTokenService;

    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Transactional
    public UserProfileDTO register(RegisterRequestDTO request) {

        boolean emailAlreadyExists = userRepository.existsByEmail(request.getEmail());
        boolean dniAlreadyExists = userRepository.existsByDni(request.getDni());

        if (emailAlreadyExists) {
            throw new BadRequestException("El email ya está siendo usado por otro usuario.");
        }

        if (dniAlreadyExists) {
            throw new BadRequestException("El DNI ya está siendo usado por otro usuario.");
        }

        User user = userMapper.convertToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(false);
        user.setRole(Role.USER);
        user.setCreatedAt(java.time.LocalDate.now());
        userRepository.save(user);

        String token = verificationTokenService.createVerificationToken(user);
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", user.getFirstName() + " " + user.getLastName());
        variables.put("token", token);
        emailSenderService.sendEmail(user.getEmail(),"Verificación de correo electrónico","verificationEmail",variables);
        return userMapper.convertToDTO(user);
    }

    @Transactional
    public AuthResponseDTO authenticate(AuthRequestDTO request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        
        User user = userRepository.findUserByEmail(request.getEmail())
            .orElseThrow();
        revokeAllUserTokens(user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);     
        saveUserToken(user, jwtToken);
        return new AuthResponseDTO(jwtToken, refreshToken, userMapper.convertToDTO(user));
    }

    @Transactional 
    public UserProfileDTO verifyEmail(String token) {
        VerificationToken verificationTokenOpt  = verificationTokenService.getVerificationToken(token)
            .orElseThrow(() -> new BadRequestException("The token is invalid"));

        if (verificationTokenOpt.getExpirationDate().isBefore(LocalDateTime.now(ZoneId.systemDefault()))) {
            verificationTokenService.deleteVerificationToken(token);
            throw new BadRequestException("The token has expired");
        }

        User user = verificationTokenOpt.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        verificationTokenService.deleteVerificationToken(token);
        return userMapper.convertToDTO(user);
    }

    @Transactional
    public void resendVerificationEmail(VerificationRequestDTO verificationRequestDTO) {
        User user = userRepository.findUserByEmail(verificationRequestDTO.getEmail())
            .orElseThrow(() -> new BadRequestException("The user does not exist"));
        if (user.isEnabled()) {
            throw new BadRequestException("The user is already verified");
        }
        // eliminamos el token anterior
        verificationTokenService.deleteVerificationTokenByUser(user.getEmail());
        String token = verificationTokenService.createVerificationToken(user);
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", user.getFirstName() + " " + user.getLastName());
        variables.put("token", token);
        emailSenderService.sendEmail(user.getEmail(),"Verificación de correo electrónico","verificationEmail",variables);
    }

    @Transactional
    public void sendPasswordResetEmail(VerificationRequestDTO verificationRequestDTO) {
        User user = userRepository.findUserByEmail(verificationRequestDTO.getEmail())
            .orElseThrow(() -> new BadRequestException("The user does not exist"));
        String token = verificationTokenService.createVerificationToken(user);
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", user.getFirstName() + " " + user.getLastName());
        variables.put("token", token);
        emailSenderService.sendEmail(user.getEmail(),"Recuperación de contraseña","passwordResetEmail",variables);
    }

    @Transactional
    public void resetPassword(ResetPassRequestDTO resetPassRequestDTO) {
        VerificationToken verificationTokenOpt = verificationTokenService.getVerificationToken(resetPassRequestDTO.getToken())
            .orElseThrow(() -> new BadRequestException("The token is invalid"));

        if (verificationTokenOpt.getExpirationDate().isBefore(LocalDateTime.now(ZoneId.systemDefault()))) {
            verificationTokenService.deleteVerificationToken(resetPassRequestDTO.getToken());
            throw new BadRequestException("The token has expired");
        }

        User user = verificationTokenOpt.getUser();
        user.setPassword(passwordEncoder.encode(resetPassRequestDTO.getNewPassword()));
        userRepository.save(user);
        verificationTokenService.deleteVerificationToken(resetPassRequestDTO.getToken());
    } 

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, StreamWriteException, DatabindException, java.io.IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
        return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
        User user = this.userRepository.findUserByEmail(userEmail)
                .orElseThrow();
        if (jwtService.isTokenValid(refreshToken, user)) {
            String accessToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, accessToken);
            AuthResponseDTO authResponse = new AuthResponseDTO(accessToken, refreshToken, userMapper.convertToDTO(user));
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        }
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);
    }
    
    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
          return;
        validUserTokens.forEach(token -> {
          token.setExpired(true);
          token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
      }
    
}
