package com.aksrua.card.dto.request;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Religion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateCardRequestDto {

	private Long userId;

	private String nickname;

	private Integer age;

	private Integer height;

	private BodyType bodyType;

	private String job;

	private String address;

	private String introduction;

	private Religion religion;
}