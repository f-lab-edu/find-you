package com.aksrua.filter.dto.response;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.filter.data.entity.Filter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateFilterResponseDto {

	private Long userId;

	private Integer minAge;

	private Integer maxAge;

	private Integer minHeight;

	private Integer maxHeight;

	private BodyType bodyType;

	private Religion religion;

	public static UpdateFilterResponseDto fromEntity(Filter updateFilter) {
		return UpdateFilterResponseDto.builder()
				.userId(updateFilter.getId())
				.minAge(updateFilter.getMinAge())
				.maxAge(updateFilter.getMaxAge())
				.minHeight(updateFilter.getMinHeight())
				.maxHeight(updateFilter.getMaxHeight())
				.bodyType(updateFilter.getBodyType())
				.religion(updateFilter.getReligion())
				.build();
	}
}
