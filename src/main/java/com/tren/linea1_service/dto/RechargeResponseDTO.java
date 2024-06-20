package com.tren.linea1_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RechargeResponseDTO {
    private String date;
    private String time;
    private String voucher;
    private String cardNumber;
    private String rechargedAmount;
    private String status;
}
