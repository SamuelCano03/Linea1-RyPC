package com.tren.linea1_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationRequestDTO {
    @NotBlank(message = "El email no puede estar vacio")
    @Email
    private String email;
}
