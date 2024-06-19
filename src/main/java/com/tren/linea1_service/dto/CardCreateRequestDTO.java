package com.tren.linea1_service.dto;

import com.tren.linea1_service.model.enums.CardType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardCreateRequestDTO {

    @NotBlank(message = "El número de tarjeta no puede estar vacio")
    @Pattern(regexp = "[0-9]{8}", message = "El número de tarjeta debe contener 8 digitos")
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private CardType cardType;
}
