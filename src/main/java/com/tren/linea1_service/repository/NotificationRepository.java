package com.tren.linea1_service.repository;

import com.tren.linea1_service.model.entity.Notification;
import com.tren.linea1_service.model.entity.User;
import lombok.Data;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUser(User user);
}
