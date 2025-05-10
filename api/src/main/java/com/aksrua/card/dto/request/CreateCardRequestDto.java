package com.aksrua.card.dto.request;

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

	private String gender;

	private String nickname;

	private Integer age;

	private String job;

	private String address;

	private String introduction;

	private String religion;

	@Override
	public String toString() {
		return "CreateCardRequestDto{" +
				"userId=" + userId +
				", gender='" + gender + '\'' +
				", nickname='" + nickname + '\'' +
				", age=" + age +
				", job='" + job + '\'' +
				", address='" + address + '\'' +
				", introduction='" + introduction + '\'' +
				", religion='" + religion + '\'' +
				'}';
	}
}