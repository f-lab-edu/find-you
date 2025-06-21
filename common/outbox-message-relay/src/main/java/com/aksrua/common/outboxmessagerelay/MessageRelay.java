package com.aksrua.common.outboxmessagerelay;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageRelay {
	private final OutboxRepository outboxRepository;
	private final KafkaTemplate<String, String> messageRelayKafkaTemplate;

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void createOutbox(OutboxEvent outboxEvent) {
		log.info("[MessageRelay.createOutbox] outboxEvent={}", outboxEvent);
		outboxRepository.save(outboxEvent.getOutbox());
	}

	@Async("messageRelayPublishEventExecutor")
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void publishEvent(OutboxEvent outboxEvent) {
		publishEvent(outboxEvent.getOutbox());
	}

	private void publishEvent(Outbox outbox) {
		try {
			messageRelayKafkaTemplate.send(
					outbox.getEventType().getTopic(),
					String.valueOf(outbox.getOutboxId()),
					outbox.getPayload()
			).get(1, TimeUnit.SECONDS);
			outboxRepository.delete(outbox);
		} catch (Exception e) {
			log.error("[MessageRelay.publishEvent] outbox={}", outbox, e);
		}
	}
}