package com.aksrua.user.controller;

import com.aksrua.common.dto.ApiResponse;
import com.aksrua.user.controller.dto.response.UserResponseDto;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.controller.dto.request.SignupRequestDto;
import com.aksrua.user.controller.dto.response.SignupResponseDto;
import com.aksrua.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class UserController {

	private final UserService userService;

	@PostMapping("/users")
	public ApiResponse<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
		//TODO: 비밀번호 암호화
		User user = requestDto.toEntity();
		User savedUser = userService.signup(user);
		return ApiResponse.of(HttpStatus.CREATED, SignupResponseDto.fromEntity(savedUser));
	}

	@GetMapping("/users/{userId}")
	public ApiResponse<UserResponseDto> getUserDetail(@PathVariable Long userId) {
		User findUser = userService.getUserDetail(userId);
		return ApiResponse.ok(UserResponseDto.fromEntity(findUser));
	}

	//TODO: 로그인

	//TODO: 회원 탈퇴
}
