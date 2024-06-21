package com.tren.linea1_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPassRequestDTO {
    @NotBlank(message = "El token no puede estar vacio")
    private String token;

    @NotBlank(message = "El password no puede estar vacio")
    private String newPassword;
}
