package com.aksrua.auth.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LoginResponseDto {
	private String token;

	public static LoginResponseDto fromString(String token) {
		return LoginResponseDto.builder()
				.token(token)
				.build();
	}
}
