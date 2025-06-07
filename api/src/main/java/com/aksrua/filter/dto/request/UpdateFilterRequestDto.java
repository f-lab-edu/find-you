package com.aksrua.filter.dto.request;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.filter.data.entity.Filter;
import com.aksrua.user.data.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateFilterRequestDto {

	private Integer minAge;

	private Integer maxAge;

	private Integer minHeight;

	private Integer maxHeight;

	private BodyType bodyType;

	private Religion religion;

	public Filter toEntity(User findUser) {
		return Filter.builder()
				.user(findUser)
				.minAge(minAge)
				.maxAge(maxAge)
				.minHeight(minHeight)
				.maxHeight(maxHeight)
				.bodyType(bodyType)
				.religion(religion)
				.build();
	}
}
