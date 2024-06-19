package com.tren.linea1_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tren.linea1_service.dto.CardRechargeRequestDTO;
import com.tren.linea1_service.dto.RechargeResponseDTO;
import com.tren.linea1_service.dto.YapeRechargeRequestDTO;
import com.tren.linea1_service.service.RechargeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recharge")
public class RechargeController {
    private final RechargeService rechargeService;

    @PostMapping("/card")
    public ResponseEntity<RechargeResponseDTO> rechargeCardViaCard(@Validated @RequestBody CardRechargeRequestDTO cardRechargeRequestDTO) {
        RechargeResponseDTO response = rechargeService.rechargeCardViaCard(cardRechargeRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/yape")
    public ResponseEntity<RechargeResponseDTO> rechargeCardViaYape(@Validated @RequestBody YapeRechargeRequestDTO yapeRechargeRequestDTO) {
        RechargeResponseDTO response = rechargeService.rechargeCardViaYape(yapeRechargeRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<RechargeResponseDTO>> getAllRecharges() {
        List<RechargeResponseDTO> recharges = rechargeService.getAllRecharges();
        return new ResponseEntity<>(recharges, HttpStatus.OK);
    }

    @GetMapping("/list/card")
    public ResponseEntity<List<RechargeResponseDTO>> getRechargesByCardNumber(@RequestParam String cardNumber) {
        List<RechargeResponseDTO> recharges = rechargeService.getAllRechargesByCardNumber(cardNumber);
        return new ResponseEntity<>(recharges, HttpStatus.OK);
    }
}
