package com.tren.linea1_service.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class NotificationResponseDTO {
    private String title;
    private String description;
    private LocalDateTime notificationDate;
}