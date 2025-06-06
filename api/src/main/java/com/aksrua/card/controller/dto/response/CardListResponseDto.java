package com.aksrua.card.controller.dto.response;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.entity.Religion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CardListResponseDto {
	private Long cardId;

	private String nickname;

	private Integer age;

	private Integer height;

	private BodyType bodyType;

	private String job;

	private String address;

	private String introduction;

	private String imagesUrl;

	private Religion religion;

	public static CardListResponseDto fromEntity(Card card) {
		return CardListResponseDto.builder()
				.cardId(card.getUser().getId())
				.nickname(card.getNickname())
				.age(card.getAge())
				.height(card.getHeight())
				.bodyType(card.getBodyType())
				.job(card.getJob())
				.address(card.getAddress())
				.introduction(card.getIntroduction())
				.imagesUrl(card.getImagesUrl())
				.religion(card.getReligion())
				.build();
	}
}