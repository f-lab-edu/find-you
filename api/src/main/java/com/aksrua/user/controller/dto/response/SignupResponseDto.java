package com.aksrua.user.controller.dto.response;

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

	//TODO: db의 id를 보여줄 필요가 있을까 ? email로 변경 ?
	private Long userId;

	public static SignupResponseDto fromEntity(User user) {
		return SignupResponseDto.builder()
				.userId(user.getId())
				.build();
	}
}
