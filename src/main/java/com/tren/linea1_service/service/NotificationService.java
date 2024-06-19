package com.tren.linea1_service.service;

import com.tren.linea1_service.exception.ResourceNotFoundException;
import com.tren.linea1_service.mapper.CouponMapper;
import com.tren.linea1_service.mapper.NotificationMapper;
import com.tren.linea1_service.mapper.UserMapper;
import com.tren.linea1_service.model.dto.CouponRequestDTO;
import com.tren.linea1_service.model.dto.CouponResponseDTO;
import com.tren.linea1_service.model.dto.NotificationRequestDTO;
import com.tren.linea1_service.model.dto.NotificationResponseDTO;
import com.tren.linea1_service.model.entity.Coupon;
import com.tren.linea1_service.model.entity.Notification;
import com.tren.linea1_service.model.entity.User;
import com.tren.linea1_service.repository.CouponRepository;
import com.tren.linea1_service.repository.NotificationRepository;
import com.tren.linea1_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    NotificationRepository notificationRepository;
    NotificationMapper notificationMapper;

    public Notification addNotification(NotificationRequestDTO notificationRequestDTO){
        if(SecurityContextHolder.getContext().getAuthentication() == null){
            throw new ResourceNotFoundException("Not logged in");
        }
        Optional<User> user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Notification notification = notificationMapper.convertToEntity(notificationRequestDTO);
        notification.setUser(user.get());
        notification.setNotificationDate(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public List<NotificationResponseDTO> pushNotifications(){
        if(SecurityContextHolder.getContext().getAuthentication() == null){
            throw new ResourceNotFoundException("Not logged in");
        }
        Optional<User> user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Notification> notifications = notificationRepository.findByUser(user.get());
        return notificationMapper.convertToDtoList(notifications);
    }
}