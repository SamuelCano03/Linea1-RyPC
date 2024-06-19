package com.tren.linea1_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tren.linea1_service.dto.CardAddRequestDTO;
import com.tren.linea1_service.dto.CardCreateRequestDTO;
import com.tren.linea1_service.dto.CardDetailsDTO;
import com.tren.linea1_service.dto.CardStateChangeDTO;
import com.tren.linea1_service.service.CardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    @PostMapping("/admin/create")
    public ResponseEntity<CardDetailsDTO> createCard(@Validated @RequestBody 
                                                    CardCreateRequestDTO cardCreateRequestDTO) {
        CardDetailsDTO card = cardService.adminCreateCard(cardCreateRequestDTO);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @PutMapping("/admin/block")
    public ResponseEntity<String> blockCard(@RequestParam String cardNumber) {
        cardService.adminBlockCard(cardNumber);
        return new ResponseEntity<>("Card blocked", HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<CardDetailsDTO> addCard(@Validated @RequestBody 
                                                CardAddRequestDTO cardCreateRequestDTO) {
        CardDetailsDTO card = cardService.addCard(cardCreateRequestDTO);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<List<CardDetailsDTO>> getCardDetails() {
        List<CardDetailsDTO> cards = cardService.getAllCardDetails();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @PutMapping("/block")
    public ResponseEntity<String> blockCard(@Validated @RequestBody 
                                            CardStateChangeDTO cardStateChangeDTO) {
        cardService.blockCard(cardStateChangeDTO);
        return new ResponseEntity<>("Card blocked", HttpStatus.OK);
    }

    @PutMapping("/unlink")
    public ResponseEntity<String> unlinkCard(@Validated @RequestBody 
                                            CardStateChangeDTO cardStateChangeDTO) {
        cardService.unlinkCard(cardStateChangeDTO);
        return new ResponseEntity<>("Card unlinked", HttpStatus.OK);
    }
}
