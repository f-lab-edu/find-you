package com.aksrua.card.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.aksrua.card.dto.response.CardResponseDto;
import com.aksrua.card.repository.CardRepository;
import com.aksrua.card.vo.Card;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

	@Mock
	private CardRepository cardRepository;

	@InjectMocks
	private CardService cardService;

	@Test
	@DisplayName("열_장의_소개팅_카드를_받는다")
	void getCardList() {
		List<String> imagesUrl = new ArrayList<>();
		imagesUrl.add("sampleUrl1.jpg");
		imagesUrl.add("sampleUrl2.jpg");
		imagesUrl.add("sampleUrl3.jpg");

		List<String> hobbies = new ArrayList<>();
		hobbies.add("독서");
		hobbies.add("코딩");
		hobbies.add("음악감상");

		List<Card> mockUsers = new ArrayList<>();

		for (int i = 1; i <= 10; i++) {
			Card card = Card.builder()
					.id((long) i)
					.gender("F")
					.nickname("sample" + i)
					.age(27)
					.job("디자이너")
					.address("서울시 은평구")
					.distanceKm(23)
					.imagesUrl(imagesUrl)
					.hobbies(hobbies)
					.build();
			mockUsers.add(card);
		}

		when(cardRepository.getCardList()).thenReturn(mockUsers);

		// when
		List<CardResponseDto> cardList = cardService.getCardList();

		// then
		assertEquals(10, cardList.size());
	}
}