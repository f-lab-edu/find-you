package com.aksrua.card.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
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
}