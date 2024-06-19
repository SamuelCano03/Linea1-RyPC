package com.tren.linea1_service.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CouponResponseDTO {
    private String description;
    private Date startDate;
    private Date endDate;
    private String type;
    private String value;
}