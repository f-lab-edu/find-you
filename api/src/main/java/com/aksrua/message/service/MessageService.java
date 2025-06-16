package com.aksrua.message.service;

import com.aksrua.event.EventPublisher;
import com.aksrua.event.MessageSentEvent;
import com.aksrua.message.data.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * TODO: 추후 유저간 message 기능 개발 예정
 */
@RequiredArgsConstructor
@Service
public class MessageService {

	private final EventPublisher eventPublisher;

	public void sendMessage(Message message) {
		// validation 로직
		MessageSentEvent event = new MessageSentEvent(
				message.getSenderCardId(),
				message.getReceiverCardId(),
				message.getContent(),
				message.getSentTime()
		);
		eventPublisher.publishMessageSentEvent(event);
	}
}
