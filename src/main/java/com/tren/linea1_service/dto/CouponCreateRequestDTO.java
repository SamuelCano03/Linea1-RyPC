package com.tren.linea1_service.dto;

import com.tren.linea1_service.model.enums.CouponType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponCreateRequestDTO {
    @NotBlank(message = "El codigo del cupon no puede estar vacio")
    private String code;

    @Enumerated(EnumType.STRING)
    private CouponType type;

    @NotNull(message = "El valor del descuento no puede estar vacio")
    @Min(value = 0, message = "El valor del descuento debe ser mayor o igual a 0")
    private Double discountValue;

    @NotNull(message = "El monto minimo no puede estar vacio")
    @Min(value = 0, message = "El monto minimo debe ser mayor o igual a 0")
    private Double minAmount;

    @NotBlank(message = "La fecha de inicio no puede estar vacia")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha de inicio debe tener el formato yyyy-MM-dd")
    private String startDate;

    @NotBlank(message = "La fecha de expiracion no puede estar vacia")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha de expiracion debe tener el formato yyyy-MM-dd")
    private String expirationDate;

    private String description;

    @NotNull(message = "La cantidad de usos no puede estar vacia")
    @Min(value = 1, message = "La cantidad de usos debe ser mayor a 0")
    private int maxUsageCount;

    @NotNull(message = "La cantidad de cupones a crear no puede estar vacia")
    @Min(value = 1, message = "La cantidad de cupones a crear debe ser mayor a 0")
    private int quantity;
}


