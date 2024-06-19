package com.tren.linea1_service.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Coupon")
@Data
@RequiredArgsConstructor

public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "description")
    private String description;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date", nullable = false)
    private Date endDate;
    @Column(name = "value", nullable = false)
    private float value;
}
