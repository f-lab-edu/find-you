package com.aksrua.card.service;

import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.repository.CardRepository;
import com.aksrua.like.data.entity.Like;
import com.aksrua.like.data.repository.LikeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CardService {
	private final CardRepository cardRepository;
	private final LikeRepository likeRepository;

	/**
	 * @Desc: 가입된 회원의 프로필 카드를 만든다.
	 */
	public Card createCard(Card card) {
		return cardRepository.save(card);
	}

	/**
	 * @return Card list 10장
	 * @Desc: 특정 시간마다 보여주는 10장의 소개팅 카드
	 */
	public List<Card> getCardList() {
		//TODO: userId 를 통해 필터링 데이터를 조회한다.
		return cardRepository.findTop10ByOrderByCreatedAtDesc();
	}

	public List<Card> getSentLikedCards(Long cardId) {
		List<Like> sentLikesList = likeRepository.findBySenderCardId(cardId);
		return sentLikesList.stream()
				.map(Like::getReceiverCard)
				.collect(Collectors.toList());
	}

	public List<Card> getCardLikedByOthers(Long cardId) {
		List<Like> receivedLikesList = likeRepository.findByReceiverCardId(cardId);
		return receivedLikesList.stream()
				.map(Like::getSenderCard)
				.collect(Collectors.toList());
	}
}
