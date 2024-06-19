package com.tren.linea1_service.controller;

import com.tren.linea1_service.dto.NotificationRequestDTO;
import com.tren.linea1_service.dto.NotificationResponseDTO;
import com.tren.linea1_service.model.Notification;
import com.tren.linea1_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/all")
    public ResponseEntity<List<NotificationResponseDTO>> getAllNotifications(){
        List<NotificationResponseDTO> not = notificationService.pushNotifications();
        return ResponseEntity.ok(not);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public ResponseEntity<Notification> addNotification(@RequestBody NotificationRequestDTO dto){
        Notification not = notificationService.addNotification(dto);
        return ResponseEntity.ok(not);
    }


}