package com.aksrua.user.dto.response;

import com.aksrua.user.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponseDto {

	private Long userId;

	public static SignupResponseDto fromEntity(User user) {
		return SignupResponseDto.builder()
				.userId(user.getId())
				.build();
	}
}
