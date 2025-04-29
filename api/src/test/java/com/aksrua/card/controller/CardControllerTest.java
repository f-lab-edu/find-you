package com.aksrua.card.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import com.aksrua.card.dto.response.CardResponseDto;
import com.aksrua.card.service.CardService;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CardControllerTest {

	private MockMvc mockMvc;
	private CardService cardService;

	@BeforeEach
	void setUp() {
		cardService = Mockito.mock(CardService.class);
		CardController cardController = new CardController(cardService);
		mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
	}

	@Test
	@DisplayName("10장의 소개팅 카드를 받는다")
	public void getCardList() throws Exception {
		List<CardResponseDto> mockCardList = IntStream.range(0, 10)
				.mapToObj(i -> CardResponseDto.builder()
						.id((long) i)
						.nickname("user" + i)
						.age(22 + i)
						.gender("F")
						.distanceKm(20 + i)
						.imagesUrl(null)
						.hobbies(null)
						.build())
				.toList();

		when(cardService.getCardList()).thenReturn(mockCardList);

		mockMvc.perform(get("/api/v1/cards").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(10)));
	}
}