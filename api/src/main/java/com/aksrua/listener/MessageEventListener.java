package com.aksrua.listener;

import com.aksrua.event.MessageSentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Component
public class MessageEventListener {

//	@KafkaListener(topics = "find-you-message", groupId = "dating-app-group")
	@KafkaListener(topics = "find-you-message")
	@Transactional
	public void handleMessageSentEvent(
			@Payload MessageSentEvent event,
			@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
			@Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
			@Header(KafkaHeaders.OFFSET) long offset,
			Acknowledgment acknowledgment) {

		log.info("Received MessageSentEvent from topic: {}, partition: {}, offset: {}",
				topic, partition, offset);

		try {
			//TODO: 코인차감
			log.info(" ::: in messageEvent -> deduce coin ::: ");

			//TODO: Message repository 저장
			log.info(" ::: in messageEvent -> send message ::: ");

			//TODO: 알림전송
			log.info(" ::: in messageEvent -> send notification ");

			// 메시지 처리 완료 확인
			acknowledgment.acknowledge();

		} catch (Exception e) {
			log.error("Error processing MessageSentEvent", e);
			// 에러 발생시 재시도하지 않고 DLQ로 보내거나 별도 에러 처리
		}
	}
}
