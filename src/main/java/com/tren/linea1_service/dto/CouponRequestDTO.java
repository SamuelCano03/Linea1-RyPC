package com.tren.linea1_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Registered;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;

@RequiredArgsConstructor
@Data

public class CouponRequestDTO {
    @NotBlank
    private String description;
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @NotBlank
    private String type;
    @NotBlank
    @NumberFormat
    private float value;
}