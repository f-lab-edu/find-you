package com.aksrua.common;

import com.aksrua.common.payload.LikeEventPayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Getter
public enum EventType {
	LIKE(LikeEventPayload.class, Topic.FIND_YOU_LIKE);

	private final Class<? extends EventPayload> payloadClass;
	private final String topic;

	public static EventType from(String type) {
		try {
			return valueOf(type);
		} catch(Exception e) {
			log.error("[EventType.from] type={}", type, e);
			return null;
		}
	}

	public static class Topic {
		public static final String FIND_YOU_LIKE = "find-you-like";
	}

}
