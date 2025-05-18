package com.aksrua.card.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.aksrua.card.data.entity.Card;
import com.aksrua.card.dto.request.CreateCardRequestDto;
import com.aksrua.card.service.CardService;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.data.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(CardController.class)
class CardControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;


	@Mock
	private UserRepository userRepository;

	private CardController cardController;

	private User testUser;
	private Card testCard;
	private CreateCardRequestDto createCardRequestDto;
	private List<Card> cardList;

	@BeforeEach
	void setUp() {
		// Controller 초기화
//		cardController = new CardController(cardService, userRepository);
		mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();

		// 테스트 User 객체 생성
		testUser = User.builder()
				.id(1L)
				.username("김용환")
				.email("clazziquai01@gmail.com")
				.phoneNumber("010-7617-2221")
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();

		// 테스트 Card 객체 생성
		testCard = Card.builder()
				.id(1L)
				.user(testUser)
				.gender("MALE")
				.nickname("cardNickname")
				.age(25)
				.job("Developer")
				.address("Seoul")
				.introduction("안녕하세요")
				.religion("기독교")
				.distanceKm(5.0)
				.imagesUrl("imageUrl")
				.hobbies("hobbies")
				.build();

		// 요청 DTO 생성
		createCardRequestDto = CreateCardRequestDto.builder()
				.userId(1L)
				.gender("male")
				.nickname("cardNickname")
				.age(25)
				.job("Developer")
				.address("Seoul")
				.introduction("Hello World")
				.religion("None")
				.build();

		// 카드 목록 생성
		Card testCard2 = Card.builder()
				.id(2L)
				.user(testUser)
				.gender("female")
				.nickname("cardNickname2")
				.age(28)
				.job("Designer")
				.address("Busan")
				.introduction("Hello World 2")
				.religion("Buddhism")
				.distanceKm(10.0)
				.imagesUrl("imageurl")
				.hobbies("hobbies")
				.build();

		cardList = Arrays.asList(testCard, testCard2);
	}

	@Test
	@DisplayName("카드 생성 성공 테스트")
	void createCardSuccess() throws Exception {
		//given
		when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
//		when(cardService.createCard(any(Card.class))).thenReturn(testCard);

		// when
		ResultActions resultActions = mockMvc.perform(
				post("/api/v1/cards")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(createCardRequestDto))
		);

		// then
		resultActions
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.gender", is("male")))
				.andExpect(jsonPath("$.nickname", is("cardNickname")))
				.andExpect(jsonPath("$.age", is(25)))
				.andExpect(jsonPath("$.job", is("Developer")))
				.andExpect(jsonPath("$.address", is("Seoul")))
				.andExpect(jsonPath("$.introduction", is("Hello World")))
				.andExpect(jsonPath("$.religion", is("None")));
	}

	@Test
	@DisplayName("유저 없을 때 카드 생성 실패 테스트")
	void createCardFailUserNotFound() throws Exception {
		// given
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		// when
		ResultActions resultActions = mockMvc.perform(
				post("/api/v1/cards")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(createCardRequestDto))
		);

		// then
		resultActions.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("카드 목록 조회 성공 테스트")
	void getCardsListSuccess() throws Exception {
		// given
//		given(cardService.getCardList()).willReturn(cardList);

		// when
		ResultActions resultActions = mockMvc.perform(
				get("/api/v1/cards")
						.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].gender", is("male")))
				.andExpect(jsonPath("$[0].nickname", is("cardNickname")))
				.andExpect(jsonPath("$[0].distanceKm", is(5.0)))
				.andExpect(jsonPath("$[0].imagesUrl", hasSize(2)))
				.andExpect(jsonPath("$[0].hobbies", hasSize(2)))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].gender", is("female")))
				.andExpect(jsonPath("$[1].nickname", is("cardNickname2")));
	}

	@Test
	@DisplayName("EntityNotFoundException 예외 처리 테스트")
	void handleEntityNotFoundException() throws Exception {
		// given
		when(userRepository.findById(1L)).thenThrow(new EntityNotFoundException("회원조회가 되지 않습니다."));

		// when
		ResultActions resultActions = mockMvc.perform(
				post("/api/v1/cards")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(createCardRequestDto))
		);

		// then
		resultActions.andExpect(status().isNotFound());
	}
}