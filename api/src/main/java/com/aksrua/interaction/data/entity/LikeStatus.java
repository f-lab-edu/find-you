package com.aksrua.interaction.data.entity;

import lombok.Getter;

@Getter
public enum LikeStatus {
	SENT("좋아요 보냄"),
	CHECKED("상대방이 확인함"),
	MATCHED("매칭이 성사됨");

	private final String description;

	LikeStatus(String description) {
		this.description = description;
	}
}
