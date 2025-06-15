package com.aksrua.auth.controller;

import com.aksrua.auth.controller.dto.request.LoginRequestDto;
import com.aksrua.auth.controller.dto.response.LoginResponseDto;
import com.aksrua.auth.service.AuthTokenService;
import com.aksrua.common.dto.ApiResponse;
import com.aksrua.common.exception.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AuthController {

	private final AuthTokenService authTokenService;

	@PostMapping("/login")
	public ApiResponse<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
		LoginResponseDto responseDto = LoginResponseDto.fromString(
				authTokenService.login(requestDto.getEmail(), requestDto.getPassword()));
		return ApiResponse.ok(responseDto);
	}

	@PostMapping("/logout")
	public ApiResponse<String> logout(HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		if (token == null) {
			throw new AuthenticationException("토큰이 존재하지 않습니다.");
		}
		authTokenService.logout(token);
		return ApiResponse.ok("로그아웃 되었습니다.");
	}
}
