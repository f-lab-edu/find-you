package com.aksrua.interaction.dto.response;

import com.aksrua.card.data.entity.Card;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SentLikeCardsListResponseDto {

	private Long cardId;

	private String nickname;

	private Integer age;

	private String address;

	private String imagesUrl;

	public static SentLikeCardsListResponseDto fromEntity(Card card) {
		return SentLikeCardsListResponseDto.builder()
				.cardId(card.getId())
				.nickname(card.getNickname())
				.age(card.getAge())
				.address(card.getAddress())
				.imagesUrl(card.getImagesUrl())
				.build();
	}
}
