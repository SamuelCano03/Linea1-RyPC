package com.tren.linea1_service.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.DataAmount;
import lombok.Data;

import java.sql.Date;

@Data
public class CouponResponseDTO {
    private String description;
    private String discount;
    private Date startDate;
    private Date endDate;
    private String type;
    private String value;
}
