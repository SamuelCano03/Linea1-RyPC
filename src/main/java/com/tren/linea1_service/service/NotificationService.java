package com.tren.linea1_service.service;

import com.tren.linea1_service.dto.NotificationRequestDTO;
import com.tren.linea1_service.dto.NotificationResponseDTO;
import com.tren.linea1_service.exceptions.ResourceNotFoundException;
import com.tren.linea1_service.mapper.NotificationMapper;
import com.tren.linea1_service.mapper.UserMapper;
import com.tren.linea1_service.model.Notification;
import com.tren.linea1_service.model.User;
import com.tren.linea1_service.repository.NotificationRepository;
import com.tren.linea1_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public Notification addNotification(NotificationRequestDTO notificationRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> user = userRepository.findUserByEmail(userDetails.getUsername());
        Notification notification = notificationMapper.convertToEntity(notificationRequestDTO);
        notification.setUser(user.get());
        notification.setNotificationDate(LocalDateTime.now());
        notification.setRead(false);
        return notificationRepository.save(notification);
    }

    public List<NotificationResponseDTO> pushNotifications(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> user = userRepository.findUserByEmail(userDetails.getUsername());
        List<Notification> notifications = notificationRepository.findByUserAndReadIsFalse(user.get());
        notifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(notifications);

        return notificationMapper.convertToDtoList(notifications);
    }
}