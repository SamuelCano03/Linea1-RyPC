package com.tren.linea1_service.dto;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = "El nombre del usuario no puede estar vacio")
    @Pattern(regexp = "[A-Za-z ]+", message = "El nombre solo debe contener letras")
    private String firstname;

    @NotBlank(message = "El apellido del usuario no puede estar vacio")
    @Pattern(regexp = "[A-Za-z ]+", message = "El apellido solo debe contener letras")
    private String lastname;

    @NotBlank(message = "El DNI del usuario no puede estar vacio")
    @Pattern(regexp = "[0-9]{8}", message = "El DNI debe contener 8 digitos")
    private String dni;

    @NotBlank(message = "El email del usuario no puede estar vacio")
    @Email
    private String email;

    @NotBlank(message = "La contraseña del usuario no puede estar vacia")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;
}