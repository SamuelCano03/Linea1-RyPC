package com.tren.linea1_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO{

    @NotBlank(message = "El email del usuario no puede estar vacio")
    @Email(message = "El email debe ser valido")
    private String email;
    
    @NotBlank(message = "La contraseña del usuario no puede estar vacia")
    private String password;
}
