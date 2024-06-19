package com.tren.linea1_service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.tren.linea1_service.dto.CardCreateRequestDTO;
import com.tren.linea1_service.dto.CardDetailsDTO;
import com.tren.linea1_service.model.Card;

import lombok.AllArgsConstructor;

import java.util.List;

@Component
@AllArgsConstructor
public class CardMapper {
    private final ModelMapper modelMapper;

    public Card convertToEntity(CardCreateRequestDTO cardCreateRequestDTO){
        return  modelMapper.map(cardCreateRequestDTO, Card.class);
    }

    public CardDetailsDTO convertToDTO(Card card){
        return  modelMapper.map(card, CardDetailsDTO.class);
    }

    public List<CardDetailsDTO> convertToDTOList(List<Card> cards){
        return cards.stream()
        .map(this::convertToDTO)
        .toList();
    }
}
