package com.aksrua.user.controller;

import static com.aksrua.user.data.entity.Gender.MALE;
import static org.junit.jupiter.api.Assertions.*;
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
import com.aksrua.card.controller.dto.response.CreateCardResponseDto;
import com.aksrua.common.dto.ApiResponse;
import com.aksrua.common.exception.DuplicateResourceException;
import com.aksrua.user.controller.dto.request.SignupRequestDto;
import com.aksrua.user.controller.dto.response.SignupResponseDto;
import com.aksrua.user.data.entity.Gender;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean
	private UserService userService;

	@DisplayName("회원가입이 정상적으로 작동한다.")
	@Test
	void createUserSuccess() throws Exception {
		// given
		SignupRequestDto requestDto = SignupRequestDto.builder()
				.username("김이름")
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("testzmail@zmail.com")
				.password("1234")
				.phoneNumber("010-5928-9203")
				.build();

		User mockUser = User.builder()
				.id(1L)
				.username("김이름")
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("testzmail@zmail.com")
				.password("1234")
				.phoneNumber("010-5928-9203")
				.build();

		when(userService.signup(any(User.class))).thenReturn(mockUser);

		// when & then
		mockMvc.perform(post("/api/v1/users")
						.content(objectMapper.writeValueAsString(requestDto))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isOk()) //TODO: created 메시지로 전송하려면 어떻게 해야하지 ?
				.andExpect(jsonPath("$.code").value(201))
				.andExpect(jsonPath("$.status").value("CREATED"))
				.andExpect(jsonPath("$.message").value("CREATED"))
				.andExpect(jsonPath("$.data.userId").value(1L));

		verify(userService, times(1)).signup(any(User.class));
	}

	@DisplayName("이메일 중복으로 인한 회원가입 실패한다.")
	@Test
	void signupServiceException() throws Exception {
		// given
		SignupRequestDto requestDto = SignupRequestDto.builder()
				.username("김이름")
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("testzmail@zmail.com")
				.password("1234")
				.phoneNumber("010-5928-9203")
				.build();

		when(userService.signup(any(User.class)))
				.thenThrow(new DuplicateResourceException("이메일이 이미 존재합니다."));

		// when & then
		mockMvc.perform(post("/api/v1/users")
						.content(objectMapper.writeValueAsString(requestDto))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.code").value(409))
				.andExpect(jsonPath("$.status").value("CONFLICT"))
				.andExpect(jsonPath("$.message").value("이메일이 이미 존재합니다."));

		verify(userService, times(1)).signup(any(User.class));
	}

	@DisplayName("유저의 상세정보를 조회한다.")
	@Test
	void getUserDetailSuccess() throws Exception {
		Long userId = 1L;
		User mockUser = User.builder()
				.id(userId)
				.username("김이름")
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("testzmail@zmail.com")
				.password("1234")
				.phoneNumber("010-5928-9203")
				.build();

		when(userService.getUserDetail(userId)).thenReturn(mockUser);

		// then
		mockMvc.perform(
						get("/api/v1/users/{userId}", userId)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value("200"))
				.andExpect(jsonPath("$.status").value("OK"))
				.andExpect(jsonPath("$.message").value("OK"))
				.andExpect(jsonPath("$.data.id").value(1L))
				.andExpect(jsonPath("$.data.username").value("김이름"))
				.andExpect(jsonPath("$.data.gender").value("MALE"))
				.andExpect(jsonPath("$.data.birthDate").value("1992-02-14"))
				.andExpect(jsonPath("$.data.email").value("testzmail@zmail.com"));
	}

	@DisplayName("존재하지 않는 회원의 정보를 조회하고, 조회를 실패한다.")
	@Test
	void getUserDetailWithNoExistUser() throws Exception {
		Long userId = 999L;
		when(userService.getUserDetail(userId))
				.thenThrow(new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

		// then
		mockMvc.perform(
						get("/api/v1/users/{userId}", userId)
				)
				.andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.code").value("500"))
				.andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
				.andExpect(jsonPath("$.message").value("회원 정보를 찾을 수 없습니다."));
	}
}