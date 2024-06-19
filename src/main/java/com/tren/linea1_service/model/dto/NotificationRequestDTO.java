package com.tren.linea1_service.model.dto;

import com.tren.linea1_service.model.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationRequestDTO {
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "notification_date")
    private LocalDateTime notificationDate;
}
