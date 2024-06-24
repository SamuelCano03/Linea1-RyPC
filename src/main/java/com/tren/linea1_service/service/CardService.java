package com.tren.linea1_service.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tren.linea1_service.dto.CardAddRequestDTO;
import com.tren.linea1_service.dto.CardCreateRequestDTO;
import com.tren.linea1_service.dto.CardDetailsDTO;
import com.tren.linea1_service.dto.CardStateChangeDTO;
import com.tren.linea1_service.exceptions.BadRequestException;
import com.tren.linea1_service.exceptions.ResourceNotFoundException;
import com.tren.linea1_service.mapper.CardMapper;
import com.tren.linea1_service.model.Card;
import com.tren.linea1_service.model.User;
import com.tren.linea1_service.repository.CardRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardMapper cardMapper;
    private final CardRepository cardRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CardDetailsDTO adminCreateCard(CardCreateRequestDTO cardCreateRequestDTO) {
        Card card = cardMapper.convertToEntity(cardCreateRequestDTO);
        boolean cardAlreadyExists = cardRepository.existsByCardNumber(card.getCardNumber());
        if (cardAlreadyExists) {
            throw new BadRequestException("Card already exists");
        }
        card.setBalance(BigDecimal.valueOf(0.0));
        card.setBlocked(false);
        card.setVinculated(false);
        cardRepository.save(card);
        return cardMapper.convertToDTO(card);
    }

    @Transactional
    public void adminBlockCard(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
        card.setBlocked(true);
        cardRepository.save(card);
    }

    @Transactional
    public CardDetailsDTO addCard(CardAddRequestDTO cardAddRequestDTO) {
        User user = userService.getUserByAuth();
        int cardCount = cardRepository.countByUserEmail(user.getEmail());
        if (cardCount >= 3) {
            throw new BadRequestException("User already has 3 cards");
        }
        Card card = cardRepository.findByCardNumber(cardAddRequestDTO.getCardNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
        if(card.isVinculated()){
            throw new BadRequestException("Card is already vinculated with a user");
        }
        card.setVinculated(true);
        card.setUser(user);
        cardRepository.save(card);
        return cardMapper.convertToDTO(card);
    }

    @Transactional(readOnly = true)
    public List<CardDetailsDTO> getAllCardDetails() {
        User user = userService.getUserByAuth();
        List<Card> cards = cardRepository.findByUserEmail(user.getEmail())
                            .orElseThrow(() -> new ResourceNotFoundException("Cards not found"));
        return cardMapper.convertToDTOList(cards);
    }

    @Transactional
    public CardDetailsDTO blockCard(CardStateChangeDTO cardStateChangeDTO) {
        User user = userService.getUserByAuth();
        Card card = cardRepository.findByCardNumberAndUserEmail(cardStateChangeDTO.getCardNumber(), user.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Card is not vinculated to user"));
        if (!passwordEncoder.matches(cardStateChangeDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid password");
        }
        if (card.isBlocked()) {
            throw new BadRequestException("Card is already blocked");
        }
        card.setBlocked(true);
        cardRepository.save(card);
        return cardMapper.convertToDTO(card);
    }

    @Transactional
    public CardDetailsDTO unlinkCard(CardStateChangeDTO cardStateChangeDTO) {
        User user = userService.getUserByAuth();
        Card card = cardRepository.findByCardNumberAndUserEmail(cardStateChangeDTO.getCardNumber(), user.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Card is not vinculated to user"));
        if (!passwordEncoder.matches(cardStateChangeDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid password");
        }
        card.setVinculated(false);
        card.setUser(null);
        cardRepository.save(card);
        return cardMapper.convertToDTO(card);
    }
    
}
