package com.tren.linea1_service.model.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.databind.annotation.EnumNaming;
import com.tren.linea1_service.model.entity.enums.Role;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Data
@RequiredArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "dni")
    private String dni;
    @Column(name = "dniImage")
    private String dniImage;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "verified", nullable = false)
    private boolean verified;
    @Enumerated(EnumType.STRING)
    private Role role;
}
