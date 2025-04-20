package com.aksrua.card.controller;

import com.aksrua.card.dto.response.CardResponseDto;
import java.util.ArrayList;
import java.util.List;
import javax.smartcardio.Card;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

	@GetMapping
	public ResponseEntity<CardResponseDto[]> getCardsList() {
		CardResponseDto[] cardList = new CardResponseDto[10];
		List<String> imgUrls = new ArrayList<>();
		List<String> hobbyTags = new ArrayList<>();

		imgUrls.add("sample1.jpg");
		imgUrls.add("sample2.jpg");
		imgUrls.add("sample3.jpg");

		hobbyTags.add("hobby1");
		hobbyTags.add("hobby2");
		hobbyTags.add("hobby3");

		cardList[0] = CardResponseDto.builder()
				.id(1L)
				.nickname("test")
				.age(32)
				.gender("M")
				.distanceKm(3.2)
				.imagesUrl(imgUrls)
				.hobbyTags(hobbyTags)
				.build();

		cardList[1] = CardResponseDto.builder()
				.id(2L)
				.nickname("test2")
				.age(31)
				.gender("M")
				.distanceKm(2.2)
				.imagesUrl(imgUrls)
				.hobbyTags(hobbyTags)
				.build();

		cardList[2] = CardResponseDto.builder()
				.id(3L)
				.nickname("test3")
				.age(28)
				.gender("M")
				.distanceKm(7.1)
				.imagesUrl(imgUrls)
				.hobbyTags(hobbyTags)
				.build();
		return ResponseEntity.status(HttpStatus.OK).body(cardList);
	}

	@PostMapping("/{cardId}/likes")
	public ResponseEntity<Void> likeCard(@PathVariable Long cardId) {
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/{cardId}/dislikes")
	public ResponseEntity<Void> dislikeCard(@PathVariable Long cardId) {
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
