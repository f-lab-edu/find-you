package com.aksrua.card.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardResponseDto {
	private Long id;
	private String nickname;
	private int age;
	private String gender;
	private double distanceKm;
	private List<String> imagesUrl;
	private List<String> hobbyTags;
}
