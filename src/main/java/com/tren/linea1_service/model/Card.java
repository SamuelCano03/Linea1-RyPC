package com.tren.linea1_service.model;

import java.math.BigDecimal;

import com.tren.linea1_service.model.enums.CardType;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cards")
public class Card { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "card_number",nullable = false)
    private String cardNumber;

    @Column(name = "balance",nullable = false)
    private BigDecimal balance;

    @Column(name = "card_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
