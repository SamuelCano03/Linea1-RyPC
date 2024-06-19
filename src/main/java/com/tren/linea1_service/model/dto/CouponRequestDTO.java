package com.tren.linea1_service.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Registered;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.sql.Date;

@RequiredArgsConstructor
@Data

public class CouponRequestDTO {
    @NotBlank
    private String description;
    @NotBlank
    private Date startDate;
    @NotBlank
    private Date endDate;
    @NotBlank
    private String type;
    @NotBlank
    private float value;
}
