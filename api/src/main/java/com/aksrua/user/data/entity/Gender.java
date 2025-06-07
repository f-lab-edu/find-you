package com.aksrua.user.data.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
	MALE(0),
	FEMALE(1);

	private final int code;
	/**
	 * enum을 모르니 api호출을 할 떄 어떤 값으로 들어오든 되는지를 모르겠다.
	 */
}