package com.aksrua.card.data.repository.dto.response;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Religion;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CardResponseDto {
	private Long userId;

	private String nickname;

	private Integer age;

	private Integer height;

	private BodyType bodyType;

	private String job;

	private String address;

	private String introduction;

	private String imagesUrl;

	private Religion religion;
}
