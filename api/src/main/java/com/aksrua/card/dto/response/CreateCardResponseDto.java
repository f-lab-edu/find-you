package com.aksrua.card.dto.response;

import com.aksrua.card.data.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCardResponseDto {

	private Long id;

	private Long userId;

	private String gender;

	private String nickname;

	public static CreateCardResponseDto fromEntity(Card card) {
		return CreateCardResponseDto.builder()
				.id(card.getId())
				.userId(card.getUser().getId())
				.gender(card.getGender())
				.nickname(card.getNickname())
				.build();
	}
}

