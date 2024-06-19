package com.tren.linea1_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassRequestDTO {
        @NotBlank(message = "La contraseña acutal no puede estar vacia")
        private String password;

        @NotBlank(message = "La nueva contraseña no puede estar vacia")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        private String newPassword;
}
