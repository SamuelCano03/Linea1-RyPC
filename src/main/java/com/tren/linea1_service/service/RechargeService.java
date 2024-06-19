package com.tren.linea1_service.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tren.linea1_service.dto.CardRechargeRequestDTO;
import com.tren.linea1_service.dto.PaymentAPIResponseDTO;
import com.tren.linea1_service.dto.RechargeResponseDTO;
import com.tren.linea1_service.dto.YapeRechargeRequestDTO;
import com.tren.linea1_service.exceptions.BadRequestException;
import com.tren.linea1_service.exceptions.ResourceNotFoundException;
import com.tren.linea1_service.mapper.RechargeMapper;
import com.tren.linea1_service.model.Card;
import com.tren.linea1_service.model.Recharge;
import com.tren.linea1_service.model.User;
import com.tren.linea1_service.model.enums.PaymentMethod;
import com.tren.linea1_service.model.enums.RechargeStatus;
import com.tren.linea1_service.repository.CardRepository;
import com.tren.linea1_service.repository.RechargeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RechargeService {
    private final RechargeMapper rechargeMapper;
    private final CardRepository cardRepository;
    private final RechargeRepository rechargeRepository;
    private final UserService userService;
    private final MockPaymentService mockPaymentService;

    @Transactional
    public RechargeResponseDTO rechargeCardViaCard(CardRechargeRequestDTO cardRechargeRequestDTO) {
        User user = userService.getUserByAuth();
        Card card = cardRepository.findByCardNumberAndUserEmail(cardRechargeRequestDTO.getTrainCardNumber(), user.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Card is not vinculated to user"));
        if (card.isBlocked()) {
            throw new BadRequestException("Card is blocked");
        }
        Recharge recharge = new Recharge();
        recharge.setVoucherNumber(Recharge.generateVoucherNumber());
        recharge.setAmount(BigDecimal.valueOf(cardRechargeRequestDTO.getAmount()));
        recharge.setStatus(RechargeStatus.PENDING);
        recharge.setPaymentMethod(cardRechargeRequestDTO.getPaymentMethod());
        recharge.setCard(card);
        recharge.setCreatedDate(LocalDate.now());
        recharge.setCreatedTime(LocalTime.now().withNano(0));

        PaymentAPIResponseDTO paymentAPIResponseDTO = mockPaymentService.processPaymentCard(cardRechargeRequestDTO);
        if (paymentAPIResponseDTO.isSuccess()) {
            recharge.setStatus(RechargeStatus.SUCCESS);
            card.setBalance(card.getBalance().add(recharge.getAmount()));
            cardRepository.save(card);
        } else {
            recharge.setStatus(RechargeStatus.REJECTED);
        }

        rechargeRepository.save(recharge);
        return rechargeMapper.convertToDTO(recharge);
    }

    @Transactional
    public RechargeResponseDTO rechargeCardViaYape(YapeRechargeRequestDTO yapeRechargeRequestDTO) {
        User user = userService.getUserByAuth();
        Card card = cardRepository.findByCardNumberAndUserEmail(yapeRechargeRequestDTO.getTrainCardNumber(), user.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Card is not vinculated to user"));
        if (card.isBlocked()) {
            throw new BadRequestException("Card is blocked");
        }
        Recharge recharge = new Recharge();
        recharge.setVoucherNumber(Recharge.generateVoucherNumber());
        recharge.setAmount(BigDecimal.valueOf(yapeRechargeRequestDTO.getAmount()));
        recharge.setStatus(RechargeStatus.PENDING);
        recharge.setPaymentMethod(PaymentMethod.YAPE);
        recharge.setCard(card);
        recharge.setCreatedDate(LocalDate.now());
        recharge.setCreatedTime(LocalTime.now().withNano(0));

        PaymentAPIResponseDTO paymentAPIResponseDTO = mockPaymentService.processPaymentYape(yapeRechargeRequestDTO);
        if (paymentAPIResponseDTO.isSuccess()) {
            recharge.setStatus(RechargeStatus.SUCCESS);
            card.setBalance(card.getBalance().add(recharge.getAmount()));
            cardRepository.save(card);
        } else {
            recharge.setStatus(RechargeStatus.REJECTED);
        }

        rechargeRepository.save(recharge);
        return rechargeMapper.convertToDTO(recharge);
    }

    @Transactional(readOnly = true)
    public List<RechargeResponseDTO> getAllRecharges() {
        User user = userService.getUserByAuth();
        List<Recharge> recharges = rechargeRepository.findByUserEmail(user.getEmail())
                            .orElseThrow(() -> new ResourceNotFoundException("This user has not made any recharges yet"));
        return rechargeMapper.convertToDTOList(recharges);
    }

    @Transactional(readOnly = true)
    public List<RechargeResponseDTO> getAllRechargesByCardNumber(String cardNumber) {
        List<Recharge> recharges = rechargeRepository.findByCardNumber(cardNumber)
                            .orElseThrow(() -> new ResourceNotFoundException("This card has not made any recharges yet"));
        return rechargeMapper.convertToDTOList(recharges);
    }
}

