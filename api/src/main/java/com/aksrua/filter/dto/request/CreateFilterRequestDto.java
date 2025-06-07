package com.aksrua.filter.dto.request;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.filter.data.entity.Filter;
import com.aksrua.user.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateFilterRequestDto {

	private Integer minAge;

	private Integer maxAge;

	private Integer minHeight;

	private Integer maxHeight;

	private BodyType bodyType;

	private Religion religion;

	public Filter toEntity(User user) {
		return Filter.builder()
				.user(user)
				.minAge(minAge)
				.maxAge(maxAge)
				.minHeight(minHeight)
				.maxHeight(maxHeight)
				.bodyType(bodyType)
				.religion(religion)
				.build();
	}
}
