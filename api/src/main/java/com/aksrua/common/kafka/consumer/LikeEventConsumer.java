package com.aksrua.common.kafka.consumer;

import com.aksrua.coin.service.CoinService;
import com.aksrua.common.Event;
import com.aksrua.common.EventPayload;
import com.aksrua.common.EventType.Topic;
import com.aksrua.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeEventConsumer {

	private final NotificationService notificationService;
	private final CoinService coinService;

	@KafkaListener(topics = {Topic.FIND_YOU_LIKE})
	public void listen(String message, Acknowledgment ack) {
		log.info(" :::common consumer -> [LikeEventConsumer.listen()] message={}", message);
		Event<EventPayload> event = Event.fromJson(message);
		if (event != null) {
			//TODO: 상대방에게 좋아요 알림 기능
			notificationService.handleEvent(event);

			//TODO: 좋아요 보낸 회원의 코인 차감 기능
			coinService.handleEvent(event);

			//TODO: 실시간 좋아요 인기도 반영
		}
		ack.acknowledge();
	}
}
