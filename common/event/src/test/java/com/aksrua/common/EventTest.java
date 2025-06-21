package com.aksrua.common;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.aksrua.common.payload.LikeEventPayload;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class EventTest {

	@Test
	void serde() {
		// given
		LikeEventPayload payload = LikeEventPayload.builder()
				.senderCardId(1L)
				.receiverCardId(2L)
				.likeStatus("SENT")
				.registeredAt(now())
				.checkedAt(null)
				.matchedAt(null)
				.build();

		Event<EventPayload> event = Event.of(
				1234L,
				EventType.LIKE,
				payload
		);

		String json = event.toJson();
		log.info(" ::: json={}", json);

		Event<EventPayload> result = Event.fromJson(json);
		log.info(" ::: result={}", result);

		assertThat(result.getEventId()).isEqualTo(1234L);
		assertThat(result.getType()).isEqualTo(event.getType());
		assertThat(result.getPayload()).isInstanceOf(payload.getClass());

		LikeEventPayload resultPayload = (LikeEventPayload) result.getPayload();
		assertThat(resultPayload.getSenderCardId()).isEqualTo(1L);
		assertThat(resultPayload.getReceiverCardId()).isEqualTo(2L);
	}
}