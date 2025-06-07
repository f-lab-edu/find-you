package com.aksrua.card.data.entity;

import lombok.Getter;

@Getter
public enum Religion {

	NONE("없음"),
	CHRISTIANITY("기독교"),
	CATHOLICISM("천주교"),
	BUDDHISM("불교");

	private final String description;

	Religion(String description) {
		this.description = description;
	}
}
