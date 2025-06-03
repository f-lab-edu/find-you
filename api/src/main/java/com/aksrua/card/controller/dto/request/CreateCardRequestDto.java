package com.aksrua.card.controller.dto.request;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.user.data.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

	@NotEmpty(message = "직업을 입력해주세요.")
	private String job;

	@NotEmpty(message = "주소를 입력해주세요.")
	private String address;

	@NotEmpty(message = "자기소개를 입력해주세요.")
	private String introduction;

	@NotNull(message = "종교를 입력해주세요.")
	private Religion religion;

	public Card toEntity(User joinedUser) {
		return Card.builder()
				.user(joinedUser)
				.nickname(nickname)
				.age(getUserAge(joinedUser.getBirthDate()))
				.height(height)
				.bodyType(bodyType)
				.job(job)
				.address(address)
				.introduction(introduction)
				.religion(religion)
				.build();
	}

	private Integer getUserAge(LocalDate birthDate) {
		LocalDate now = LocalDate.now();

		// 년도 차이 계산
		int age = now.getYear() - birthDate.getYear();

		// 생일이 아직 지나지 않았으면 나이에서 1을 뺌
		if (now.getMonthValue() < birthDate.getMonthValue() ||
				(now.getMonthValue() == birthDate.getMonthValue() && now.getDayOfMonth() < birthDate.getDayOfMonth())) {
			age--;
		}

		return age;
	}
}