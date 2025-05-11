package com.aksrua.card.controller;

import com.aksrua.card.dto.response.CardResponseDto;
import com.aksrua.card.service.CardService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

	private final CardService cardService;

	public CardController(CardService cardService) {
		this.cardService = cardService;
	}

	@GetMapping
	public ResponseEntity<List<CardResponseDto>> getCardsList() {
		return ResponseEntity.status(HttpStatus.OK).body(cardService.getCardList());
	}
}
