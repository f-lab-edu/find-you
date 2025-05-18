package com.aksrua.user.controller;

import com.aksrua.user.data.entity.User;
import com.aksrua.user.dto.request.SignupRequestDto;
import com.aksrua.user.dto.response.SignupResponseDto;
import com.aksrua.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto requestDto) {
		User user = User.builder()
				.username(requestDto.getUsername())
				.email(requestDto.getEmail())
				.phoneNumber(requestDto.getPhoneNumber())
				.build();

		User savedUser = userService.signup(user);

		return ResponseEntity.status(HttpStatus.CREATED).body(SignupResponseDto.fromEntity(savedUser));
	}
}
