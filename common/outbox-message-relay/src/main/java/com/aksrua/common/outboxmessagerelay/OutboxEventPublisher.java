package com.aksrua.common.outboxmessagerelay;

import com.aksrua.common.Event;
import com.aksrua.common.EventPayload;
import com.aksrua.common.EventType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OutboxEventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;

	public void publish(EventType type, EventPayload payload) {
		log.info("[OutboxEventPublisher.publish()] type={}", type);
		log.info("[OutboxEventPublisher.publish()] payload={}", payload);

		Outbox outbox = Outbox.create(
				type,
				Event.of(1L, type, payload).toJson()
		);
		applicationEventPublisher.publishEvent(OutboxEvent.of(outbox));
	}
}
