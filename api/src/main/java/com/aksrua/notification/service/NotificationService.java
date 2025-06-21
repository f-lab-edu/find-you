package com.aksrua.notification.service;

import com.aksrua.common.Event;
import com.aksrua.common.EventPayload;
import com.aksrua.common.payload.LikeEventPayload;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * TODO: server가 user에게 알람을 보내는 service
 * CONSUMER 역할
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

	public void handleEvent(Event<EventPayload> event) {
		LikeEventPayload payload = (LikeEventPayload) event.getPayload();
		log.info("[NotificationService.handleEvent()] payload: {}", payload.toString());

		Long senderCardId = payload.getSenderCardId();
		Long receiverCardId = payload.getReceiverCardId();
		String likeStatus = payload.getLikeStatus();

		String message = "";
		if (likeStatus.equals("SENT")) {
			message = "좋아요를 보냈습니다.";
		} else if (likeStatus.equals("CHECKED")) {
			message = "상대방이 좋아요를 확인했습니다.";
		} else if (likeStatus.equals("MATCHED")) {
			message = "상대방과 매칭이 성사되었습니다.";
		}

		sendNotification(receiverCardId, message);
	}

	public void sendNotification(Long receiverCardId, String message) {
		//TODO: 해당 상대방에게 message를 보내는 모듈 작성하기
		log.info("[NotificationService.sendNotification()] receiverCardId: {} message: {}" , receiverCardId, message);
	}
}
