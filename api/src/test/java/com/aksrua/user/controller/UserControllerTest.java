package com.aksrua.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.aksrua.card.controller.CardController;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.data.repository.UserRepository;
import com.aksrua.user.dto.request.SignupRequestDto;
import com.aksrua.user.dto.response.SignupResponseDto;
import com.aksrua.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("회원가입 성공 테스트")
	void signup_Success() throws Exception {
		// given
		SignupRequestDto requestDto = SignupRequestDto.builder()
				.username("testUser")
				.email("aaa@example.com")
				.phoneNumber("010-1234-5678")
				.build();

		String content = objectMapper.writeValueAsString(requestDto);

		// when
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(jsonPath("$.userId").exists())
				.andReturn();

		// then
		String responseBody = result.getResponse().getContentAsString();
		SignupResponseDto responseDto = objectMapper.readValue(responseBody, SignupResponseDto.class);

		// DB에 실제로 저장되었는지 확인
		User savedUser = userRepository.findById(responseDto.getUserId())
				.orElseThrow(() -> new AssertionError("User not found in database"));

		// 응답 DTO 검증
		assertThat(responseDto.getUserId()).isEqualTo(savedUser.getId());

		assertThat(savedUser.getUsername()).isEqualTo(requestDto.getUsername());
		assertThat(savedUser.getEmail()).isEqualTo(requestDto.getEmail());
		assertThat(savedUser.getPhoneNumber()).isEqualTo(requestDto.getPhoneNumber());
	}

	/*
	@Test
	@DisplayName("회원가입 시 잘못된 입력값 테스트")
	void signup_WithInvalidInput() throws Exception {
		// given
		SignupRequestDto requestDto = SignupRequestDto.builder()
				.username(null)  // 빈 사용자 이름
				.email("invalidemail2")  // 잘못된 이메일 형식
				.phoneNumber("010-1234-5678")
				.build();

		String content = objectMapper.writeValueAsString(requestDto);

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	 */
}