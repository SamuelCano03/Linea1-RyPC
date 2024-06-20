package com.tren.linea1_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YapeRechargeRequestDTO {
    @NotBlank(message = "La tarjeta de tren no puede estar vacia")
    @Pattern(regexp = "[0-9]{8}", message = "El número de tarjeta debe contener 8 digitos")
    private String trainCardNumber;

    @NotBlank(message = "El codigo de verificación no puede estar vacio")
    @Pattern(regexp = "[0-9]{6}", message = "El código de verificación debe contener 6 digitos")
    private String otp;

    @NotNull(message = "El monto no puede estar vacio")
    private Double rechargedAmount;
}