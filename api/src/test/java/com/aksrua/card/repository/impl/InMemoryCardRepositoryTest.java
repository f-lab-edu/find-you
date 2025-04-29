package com.aksrua.card.repository.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.aksrua.card.vo.Card;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InMemoryCardRepositoryTest {

	private InMemoryCardRepository cardRepository;

	@BeforeEach
	void setUp() {
		cardRepository = new InMemoryCardRepository();
	}

	@Test
	@DisplayName("열 장의 소개팅 카드를 받는다")
	void getCardList() {
		// given

		// when
		List<Card> cardsList = cardRepository.getCardList();

		// then
		assertEquals(10, cardsList.size());
	}
}