package com.aksrua.card.controller.dto.response;

import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.entity.Religion;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardResponseDto {

	private Long id;

	private Long cardId;

	private String nickname;

	private Integer age;

	private String job;

	private String address;

	private String introduction;

	private double distanceKm;

	private String imagesUrl;

	private String hobbies;

	private Religion religion;

	public static CardResponseDto fromEntity(Card card) {
		return CardResponseDto.builder()
				.cardId(card.getUser().getId())
				.nickname(card.getNickname())
				.age(card.getAge())
				.job(card.getJob())
				.address(card.getAddress())
				.introduction(card.getIntroduction())
				.imagesUrl(card.getImagesUrl())
				.hobbies(card.getHobbies())
				.religion(card.getReligion())
				.build();
	}
}
