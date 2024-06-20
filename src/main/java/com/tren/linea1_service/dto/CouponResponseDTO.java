package com.tren.linea1_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponseDTO {
    private String code;
    private String type;
    private String discountValue;
    private String minAmount;
    private String startDate;
    private String expirationDate;
    private String description;
    private int MaxUsageCount;
    private boolean isActive;
}