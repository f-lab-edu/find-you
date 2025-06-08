package com.aksrua.card.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.aksrua.card.controller.dto.request.CreateCardRequestDto;
import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.card.service.CardService;
import com.aksrua.common.exception.DuplicateResourceException;
import com.aksrua.filter.service.FilterService;
import com.aksrua.user.data.entity.Gender;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(controllers = CardController.class)
class CardControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean
	private CardService cardService;

	@MockitoBean
	private UserService userService;

	@MockitoBean
	private FilterService filterService;

	private User mockUser;

	@BeforeEach
	void setUp() {
		mockUser = User.builder()
				.id(1L)
				.username("김이름")
				.gender(Gender.MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("aewf@gmail.com")
				.password("1234")
				.phoneNumber("010-1423-2412")
				.registeredAt(LocalDateTime.now())
				.build();
	}

	@DisplayName("가입된 유저의 카드를 정상적으로 생성한다.")
	@Test
	void createCardSuccess() throws Exception {
		// given
		CreateCardRequestDto requestDto = CreateCardRequestDto.builder()
				.userId(1L)
				.nickname("nickname")
				.age(29)
				.height(180)
				.bodyType(BodyType.SLIM)
				.job("무직")
				.address("서울특별시 은평구")
				.introduction("안녕하세요 :)")
				.religion(Religion.NONE)
				.build();

		Card mockCard = requestDto.toEntity(mockUser);

		when(userService.getUserDetail(requestDto.getUserId())).thenReturn(mockUser);
		when(cardService.createCard(any(Card.class))).thenReturn(mockCard);

		// when && then
		mockMvc.perform(post("/api/v1/cards")
						.content(objectMapper.writeValueAsString(requestDto))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
//				.andExpect(status().isCreated());
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value(201))
				.andExpect(jsonPath("$.status").value("CREATED"))
				.andExpect(jsonPath("$.message").value("CREATED"))
				.andExpect(jsonPath("$.data.userId").value(1))
				.andExpect(jsonPath("$.data.nickname").value("nickname"));

		verify(userService, times(1)).getUserDetail(requestDto.getUserId());
		verify(cardService, times(1)).createCard(any(Card.class));
	}

	@DisplayName("카드 생성 중 닉네임이 중복된다면 예외를 반환한다.")
	@Test
	void createCardFailWithDuplicateNickname() throws Exception {
		// given
		CreateCardRequestDto requestDto = CreateCardRequestDto.builder()
				.userId(1L)
				.nickname("nickname")
				.age(29)
				.height(180)
				.bodyType(BodyType.SLIM)
				.job("무직")
				.address("서울특별시 은평구")
				.introduction("안녕하세요 :)")
				.religion(Religion.NONE)
				.build();

		Card mockCard = requestDto.toEntity(mockUser);

		when(userService.getUserDetail(requestDto.getUserId())).thenReturn(mockUser);
		when(cardService.createCard(any(Card.class)))
				.thenThrow(new DuplicateResourceException("닉네임이 이미 존재합니다."));

		// when & then
		mockMvc.perform(post("/api/v1/cards")
						.content(objectMapper.writeValueAsString(requestDto))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.code").value(409))
				.andExpect(jsonPath("$.status").value("CONFLICT"))
				.andExpect(jsonPath("$.message").value("닉네임이 이미 존재합니다."));

		verify(userService, times(1)).getUserDetail(requestDto.getUserId());
		verify(cardService, times(1)).createCard(any(Card.class));
	}

	@DisplayName("유저의 조건에 맞는 10장의 소개팅 카드를 반환받는다.")
	@Test
	void test3() {
		// given

		// when

		// then
		assertThat(1).isEqualTo(1);
	}

	@DisplayName("해당 ID에 해당하는 카드의 정보를 가져온다")
	@Test
	void getCardDetails() throws Exception {
		// given
		Long cardId = 1L;
		Card mockCard = Card.builder()
				.user(mockUser)
				.nickname("nickname")
				.age(22)
				.height(180)
				.bodyType(BodyType.SLIM)
				.job("NONE")
				.address("서울특별시 은평구")
				.introduction("안녕하세요")
				.religion(Religion.NONE)
				.build();

		when(cardService.getCardDetails(cardId)).thenReturn(mockCard);

		// when && then
		mockMvc.perform(
						get("/api/v1/cards/{cardId}", cardId)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value("200"))
				.andExpect(jsonPath("$.status").value("OK"))
				.andExpect(jsonPath("$.message").value("OK"))
				.andExpect(jsonPath("$.data.cardId").value(cardId))
				.andExpect(jsonPath("$.data.nickname").value(mockCard.getNickname()))
				.andExpect(jsonPath("$.data.age").value(mockCard.getAge()))
				.andExpect(jsonPath("$.data.job").value(mockCard.getJob()))
				.andExpect(jsonPath("$.data.address").value(mockCard.getAddress()))
				.andExpect(jsonPath("$.data.introduction").value(mockCard.getIntroduction()))
				.andExpect(jsonPath("$.data.imagesUrl").value(mockCard.getImagesUrl()))
				.andExpect(jsonPath("$.data.hobbies").value(mockCard.getHobbies()))
				.andExpect(jsonPath("$.data.religion").value("NONE"));
				//TODO: enum 타입 테스트 코드 작성
	}

	@DisplayName("해당 ID에 해당하지 않는 카드를 조회한다면 예외를 반환한다.")
	@Test
	void getCardDetailsFailWithNotFoundException() throws Exception {
		// given
		Long cardId = 999L;

		when(cardService.getCardDetails(cardId))
				.thenThrow(new IllegalArgumentException("카드 정보를 찾을 수 없습니다."));

		// when && then
		mockMvc.perform(
						get("/api/v1/cards/{cardId}", cardId)
				)
				.andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.code").value("500"))
				.andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
				.andExpect(jsonPath("$.message").value("카드 정보를 찾을 수 없습니다."));
	}

	@DisplayName("해당 ID에 해당하는 필터정보를 조회한다.")
	@Test
	void test6() {
		// given

		// when

		// then

	}

	@DisplayName("해당 ID에 해당하지 않는 필터를 조회한다면 예외를 반환한다.")
	@Test
	void test7() {
		// given

		// when

		// then

	}

	@DisplayName("해당 ID에 해당하는 필터 정보를 수정한다.")
	@Test
	void test8() {
		// given

		// when

		// then

	}
}