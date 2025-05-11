package com.aksrua.card.vo;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Card {
	private Long id;
	private String gender;
	private String nickname;
	private int age;
	private String job;
	private String address;
	private double distanceKm;
	private List<String> imagesUrl;
	private List<String> hobbies;
}
