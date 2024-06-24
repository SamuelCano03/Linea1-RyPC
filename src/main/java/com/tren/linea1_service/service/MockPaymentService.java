package com.tren.linea1_service.service;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.tren.linea1_service.dto.CardRechargeRequestDTO;
import com.tren.linea1_service.dto.PaymentAPIResponseDTO;
import com.tren.linea1_service.dto.YapeRechargeRequestDTO;

@Component
public class MockPaymentService {

    public PaymentAPIResponseDTO processPaymentCard(CardRechargeRequestDTO request) {
        Random random = new Random();
       /*  boolean paymentSuccess = random.nextBoolean(); */
        boolean paymentSuccess = true;
        simulateDelay(1000);
        if (paymentSuccess) {
            return new PaymentAPIResponseDTO(true,generateTransactionId() , "Payment successful");
        } else {
            return new PaymentAPIResponseDTO(false, null, "Payment failed");
        }
    }

    public PaymentAPIResponseDTO processPaymentYape(YapeRechargeRequestDTO request) {
        Random random = new Random();
        boolean paymentSuccess = random.nextBoolean();
        simulateDelay(1000);
        if (paymentSuccess) {
            return new PaymentAPIResponseDTO(true, generateTransactionId(), "Payment successful");
        } else {
            return new PaymentAPIResponseDTO(false, null, "Payment failed");
        }
    }

    private String generateTransactionId() {
        long timestamp = System.currentTimeMillis();
        String suffix = generateRandomNumericSuffix(6);
        return timestamp + suffix;
    }

    private String generateRandomNumericSuffix(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();
    }

    private void simulateDelay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while sleeping", e);
        }
    }
}