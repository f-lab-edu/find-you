package com.aksrua.filter.dto.response;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.filter.data.entity.Filter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateFilterResponseDto {

	private Long userId;

	private Integer minAge;

	private Integer maxAge;

	private Integer minHeight;

	private Integer maxHeight;

	private BodyType bodyType;

	private Religion religion;

	public static CreateFilterResponseDto fromEntity(Filter savedFilter) {
		return CreateFilterResponseDto.builder()
				.userId(savedFilter.getId())
				.minAge(savedFilter.getMinAge())
				.maxAge(savedFilter.getMaxAge())
				.minHeight(savedFilter.getMinHeight())
				.maxHeight(savedFilter.getMaxHeight())
				.bodyType(savedFilter.getBodyType())
				.religion(savedFilter.getReligion())
				.build();
	}
}
