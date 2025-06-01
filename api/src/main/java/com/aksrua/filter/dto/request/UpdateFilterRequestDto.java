package com.aksrua.filter.dto.request;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Religion;
import lombok.Getter;

@Getter
public class UpdateFilterRequestDto {

	private Long userId;

	private Integer minAge;

	private Integer maxAge;

	private Integer minHeight;

	private Integer maxHeight;

	private BodyType bodyType;

	private Religion religion;
}
