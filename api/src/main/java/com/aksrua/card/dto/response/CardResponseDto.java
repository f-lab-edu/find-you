package com.aksrua.card.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardResponseDto {
	private Long id;
	private Long userId;
	private String gender;
	private String nickname;
	private Integer age;
	private String job;
	private String address;
	private String introduction;
	private double distanceKm;
	private String imagesUrl;
	private String hobbies;
	private String religion;
}
