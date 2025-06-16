package com.aksrua.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventPublisher {
	private static final String USER_LIKED_TOPIC = "find-you-like";
	private static final String MESSAGE_SENT_TOPIC = "find-you-message";

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void publishUserLikedEvent(UserLikedEvent event) {
		try {
			kafkaTemplate.send(USER_LIKED_TOPIC, event);
			log.info(" ::: Published UserLikedEvent: {} -> {}", event.getSenderCardId(), event.getReceiverCardId());
		} catch (Exception e) {
			log.error(" ::: Failed to publish UserLikedEvent", e);
		}
	}

	public void publishMessageSentEvent(MessageSentEvent event) {
		try {
			kafkaTemplate.send(MESSAGE_SENT_TOPIC, event);
			log.info(" ::: Published MessageSentEvent: {} -> {}", event.getSenderCardId(), event.getReceiverCardId());
		} catch (Exception e) {
			log.error(" ::: Failed to publish MessageSentEvent", e);
		}
	}
}
