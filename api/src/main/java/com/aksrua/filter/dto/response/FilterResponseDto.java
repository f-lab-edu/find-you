package com.aksrua.filter.dto.response;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.filter.data.entity.Filter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FilterResponseDto {

	private Long userId;

	private Integer minAge;

	private Integer maxAge;

	private Integer minHeight;

	private Integer maxHeight;

	private BodyType bodyType;

	private Religion religion;

	public static FilterResponseDto fromEntity(Filter filter) {
		return FilterResponseDto.builder()
				.userId(filter.getUser().getId())
				.minAge(filter.getMinAge())
				.maxAge(filter.getMaxAge())
				.minHeight(filter.getMinHeight())
				.maxHeight(filter.getMaxHeight())
				.bodyType(filter.getBodyType())
				.religion(filter.getReligion())
				.build();
	}
}
