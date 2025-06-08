package com.aksrua.like.data.entity;

import lombok.Getter;

@Getter
public enum Status {
	SENT("좋아요 보냄"),
	VIEWED("상대방이 확인함"),
	MATCHED("매칭이 성사됨");

	private final String description;

	Status(String description) {
		this.description = description;
	}
}
