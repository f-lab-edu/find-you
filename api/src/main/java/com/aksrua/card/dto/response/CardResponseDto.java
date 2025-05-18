package com.aksrua.card.dto.response;

import com.aksrua.card.data.entity.Card;
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

	public static CardResponseDto fromEntity(Card card) {
		return CardResponseDto.builder()
				.id(card.getId())
				.userId(card.getUser().getId())
				.gender(card.getGender())
				.nickname(card.getNickname())
				.age(card.getAge())
				.job(card.getJob())
				.address(card.getAddress())
				.introduction(card.getIntroduction())
				.distanceKm(card.getDistanceKm())
				.imagesUrl(card.getImagesUrl())
				.hobbies(card.getHobbies())
				.religion(card.getReligion())
				.build();
	}
}
