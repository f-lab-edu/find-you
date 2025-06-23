package com.aksrua.coin.service;

import com.aksrua.common.Event;
import com.aksrua.common.EventPayload;
import com.aksrua.common.payload.LikeEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoinService {

	public void handleEvent(Event<EventPayload> event) {
		LikeEventPayload payload = (LikeEventPayload) event.getPayload();
		log.info("[CoinService.handleEvent()] payload: {}", payload.toString());
		Long senderCardId = payload.getSenderCardId();
		deductCoinsForSendingLike(senderCardId);
	}

	public boolean hasEnoughCoins() {
		log.info("[CoinService.hasEnoughCoins()] 잔여 코인 존재 여부 확인");
		return true;
	}

	public void deductCoinsForSendingLike(Long cardId) {
		//TODO: 좋아요를 보낸 cardId의 코인 차감
		log.info("[CoinService.deductCoinsForSendingLike()] cardId={} 코인 차감", cardId);
	}
}
