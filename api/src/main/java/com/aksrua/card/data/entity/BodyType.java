package com.aksrua.card.data.entity;

import lombok.Getter;

@Getter
public enum BodyType {
	SLIM("슬림"),
	SLIM_TONED("슬림탄탄"),
	NORMAL("보통"),
	MUSCULAR("근육질"),
	CHUBBY("약간통통"),
	FAT("통통");

	private final String description;

	BodyType(String description) {
		this.description = description;
	}
}