package com.tren.linea1_service.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupFormDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @Email
    @NotBlank
    private String email;
    @NotNull
    private String dni;
    @NotNull
    @Size(min = 4)
    private String password;
    public String getFullName() {
        return name + " " + lastName;
    }
}