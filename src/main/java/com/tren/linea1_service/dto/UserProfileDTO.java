package com.tren.linea1_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String dni;
    private String role;
    private boolean enabled;
}
