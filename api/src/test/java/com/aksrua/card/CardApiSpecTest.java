package com.aksrua.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.aksrua.card.dto.response.CardResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CardApiSpecTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void 카드_목록_10장_반환() {
		ResponseEntity<CardResponseDto[]> response = testRestTemplate.getForEntity("/api/v1/cards", CardResponseDto[].class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(10, response.getBody().length);
	}

	@Test
	public void 카드_필드_검증() {
		ResponseEntity<CardResponseDto[]> response = testRestTemplate.getForEntity("/api/v1/cards", CardResponseDto[].class);

		CardResponseDto[] cards = response.getBody();
		assertNotNull(cards);
		assertTrue(cards.length > 0);

		CardResponseDto card = cards[0];

		// id 필드 검증
		assertNotNull(card.getId());
		assertTrue(card.getId() > 0);

		// nickname 필드 검증
		assertNotNull(card.getNickname());
		assertFalse(card.getNickname().isEmpty());

		// age 필드 검증
		assertTrue(card.getAge() > 0);

		// gender 필드 검증
		assertNotNull(card.getGender());
		assertFalse(card.getGender().isEmpty());

		// distanceKm 필드 검증
		assertTrue(card.getDistanceKm() >= 0);

		// imagesUrl 필드 검증
		assertNotNull(card.getImagesUrl());
		assertFalse(card.getImagesUrl().isEmpty());

		// hobbyTags 필드 검증
		assertNotNull(card.getHobbyTags());
	}
}
