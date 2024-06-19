package com.tren.linea1_service.dto;


import com.tren.linea1_service.model.enums.PaymentMethod;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRechargeRequestDTO {
    @NotBlank(message = "La tarjeta de tren no puede estar vacia")
    @Pattern(regexp = "[0-9]{8}", message = "El número de tarjeta debe contener 8 digitos")
    private String trainCardNumber;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @NotBlank(message = "El titular de la tarjeta no puede estar vacio")
    private String cardHolder;

    @NotBlank(message = "El número de tarjeta no puede estar vacio")
    @Pattern(regexp = "[0-9]{16}", message = "El número de tarjeta debe contener 16 digitos")
    private String cardNumber;

    @NotBlank(message = "La fecha de expiracion no puede estar vacia")
    @Pattern(regexp = "(0[1-9]|1[0-2])/[0-9]{2}", message = "La fecha de expiracion debe tener el formato MM/YY")
    private String expiryDate;

    @NotBlank(message = "El CVV no puede estar vacio")
    @Pattern(regexp = "[0-9]{3}", message = "El CVV debe contener 3 digitos")
    private String cvv;

    @NotNull(message = "El monto no puede estar vacio")
    private Double amount;
}