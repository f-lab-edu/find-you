package com.aksrua.listener;

import com.aksrua.event.UserLikedEvent;
import com.aksrua.interaction.data.entity.Like;
import com.aksrua.interaction.data.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class LikeEventListener {

	private final LikeRepository likeRepository;
	// coinService
	// notificationService

//	@KafkaListener(topics = "find-you-like", groupId = "dating-app-group")
	@KafkaListener(topics = "find-you-like")
	@Transactional
	public void handleUserLikedEvent(
			@Payload UserLikedEvent event,
			@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
			@Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
			@Header(KafkaHeaders.OFFSET) long offset,
			Acknowledgment acknowledgment) {

		log.info("Received UserLikedEvent from topic: {}, partition: {}, offset: {}",
				topic, partition, offset);

		try {
			// TODO: 코인 차감 event

			// Like 데이터베이스에 저장 event
			Like like = new Like(
					event.getSenderCardId(),
					event.getReceiverCardId(),
					event.getLikeStatus(),
					event.getRegisteredAt(),
					event.getCheckedAt(),
					event.getMatchedAt()
			);
			likeRepository.save(like);
			log.info("Saved like: {} -> {}", event.getSenderCardId(), event.getReceiverCardId());

			// TODO: 알림 전송 event

			// 메시지 처리 완료 확인
			acknowledgment.acknowledge();

		} catch (Exception e) {
			log.error("Error processing UserLikedEvent", e);
			// 에러 발생시 재시도하지 않고 DLQ(Dead Letter Queue)로 보내거나 별도 에러 처리
		}
	}
}
