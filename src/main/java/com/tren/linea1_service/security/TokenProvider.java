package com.tren.linea1_service.security;

import com.tren.linea1_service.exception.ResourceNotFoundException;
import com.tren.linea1_service.model.entity.User;
import com.tren.linea1_service.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Component
public class TokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.validity-in-seconds}")
    private long jwtValidityInSeconds;

    private Key key;
    private JwtParser jwtParser;

    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build();
    }

    public String createAccessToken(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userRepository.findOneByEmail(email);
        if(user.isEmpty()) {
            throw new ResourceNotFoundException("Email no encontrado");
        }
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim("name", user.get().getName())
                .claim("lastName", user.get().getLast_name())
                .claim("email", user.get().getEmail())
                .claim("password", user.get().getPassword())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(System.currentTimeMillis() + jwtValidityInSeconds * 1000))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        String email = claims.getSubject();
        org.springframework.security.core.userdetails.User principal =
                new org.springframework.security.core.userdetails.User(email, "", Collections.emptyList());
        return new UsernamePasswordAuthenticationToken(principal, token, Collections.emptyList());
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}