package com.tren.linea1_service.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;


import com.tren.linea1_service.model.enums.PaymentMethod;
import com.tren.linea1_service.model.enums.RechargeStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recharges")
public class Recharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RechargeStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "created_date", nullable = false)
    LocalDate createdDate;

    @Column(name = "created_time", nullable = false)
    LocalTime createdTime;

    @Column(name = "voucher_number", nullable = false)
    private String voucherNumber;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    
    public static String generateVoucherNumber() {
        long timestamp = System.currentTimeMillis();
        String suffix = generateRandomNumericSuffix(6);
        return timestamp + suffix;
    }

    private static String generateRandomNumericSuffix(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
    
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // Genera un dígito aleatorio (0-9)
            sb.append(digit);
        }
    
        return sb.toString();
    }
}
