package com.aksrua.card.controller.dto.request;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.user.data.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCardRequestDto {

	private Long userId;

	@NotBlank(message = "닉네임을 입력해주세요.")
	private String nickname;

	private Integer age;

	@Positive
	private Integer height;

	@NotNull(message = "체형을 입력해주세요.")
	private BodyType bodyType;

	@NotBlank(message = "직업을 입력해주세요.")
	private String job;

	@NotBlank(message = "주소를 입력해주세요.")
	private String address;

	@NotBlank(message = "자기소개를 입력해주세요.")
	private String introduction;

	@NotNull(message = "종교를 입력해주세요.")
	private Religion religion;

	public Card toEntity(User joinedUser) {
		return Card.builder()
				.user(joinedUser)
				.nickname(nickname)
				.age(calculateUserAge(joinedUser.getBirthDate()))
				.height(height)
				.bodyType(bodyType)
				.job(job)
				.address(address)
				.introduction(introduction)
				.religion(religion)
				.build();
	}

	private Integer calculateUserAge(LocalDate birthDate) {
		LocalDate now = LocalDate.now();

		int age = now.getYear() - birthDate.getYear();
		if (now.getMonthValue() < birthDate.getMonthValue() ||
				(now.getMonthValue() == birthDate.getMonthValue() && now.getDayOfMonth() < birthDate.getDayOfMonth())) {
			age--;
		}
		return age;
	}
}