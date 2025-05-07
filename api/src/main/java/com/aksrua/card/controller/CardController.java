package com.aksrua.card.controller;

import com.aksrua.card.data.entity.Card;
import com.aksrua.card.dto.response.CardResponseDto;
import com.aksrua.card.service.CardService;
import java.util.ArrayList;
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
		List<CardResponseDto> responseDtoList = new ArrayList<>();
		List<Card> returnCardList = cardService.getCardList();

		for (Card card : returnCardList) {
			responseDtoList.add(CardResponseDto.builder()
					.id(card.getId())
					.userId(card.getUserId())
					.gender(card.getGender())
					.nickname(card.getNickname())
					.age(card.getAge())
					.job(card.getJob())
					.address(card.getAddress())
					.introduction(card.getIntroduction())
					.distanceKm(card.getDistanceKm())
					.imagesUrl(card.getImagesUrl())
					.hobbies(card.getHobbies())
					.religion(card.getReligion())
					.build());
		}
		return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
	}
}
