package com.aksrua.user.controller.dto.response;

import com.aksrua.user.data.entity.Gender;
import com.aksrua.user.data.entity.User;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserResponseDto {

	private Long id;

	private String username;

	private Gender gender;

	private LocalDate birthDate;

	private String email;

	public static UserResponseDto fromEntity(User findUser) {
		return UserResponseDto.builder()
				.id(findUser.getId())
				.username(findUser.getUsername())
				.gender(findUser.getGender())
				.birthDate(findUser.getBirthDate())
				.email(findUser.getEmail())
				.build();
	}
}
