package com.tren.linea1_service.model.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String email;
    private String password;
}