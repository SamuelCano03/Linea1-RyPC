package com.tren.linea1_service.model.dto;

import lombok.Data;

@Data
public class UserProfileDTO {
    private String name;
    private String lastName;
    private String fullName;
    private String email;
    private String password;
}
