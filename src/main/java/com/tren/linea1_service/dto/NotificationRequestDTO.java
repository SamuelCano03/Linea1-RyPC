package com.tren.linea1_service.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class NotificationRequestDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}