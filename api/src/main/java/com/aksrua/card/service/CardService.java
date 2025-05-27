package com.aksrua.card.service;

import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.repository.CardRepository;
import com.aksrua.filter.data.entity.Filter;
import com.aksrua.filter.service.FilterService;
import com.aksrua.like.data.entity.Like;
import com.aksrua.like.data.repository.LikeRepository;
import com.aksrua.user.data.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardService {

	private final CardRepository cardRepository;
	private final LikeRepository likeRepository;
	private final FilterService filterService ;

	/**
	 * @Desc: 가입된 회원의 프로필 카드를 만든다.
	 */
	public Card createCard(Card card, User user) {
		// filter repository 기본값으로 생성.
		Filter filter = Filter.builder()
				.user(user)
				.build();
		filterService.createFilter(filter);

		return cardRepository.save(card);
	}

	/**
	 * @return Card list 10장
	 * @Desc: 특정 시간마다 보여주는 10장의 소개팅 카드
	 */
	public List<Card> getCardList(Long userId) {
		/**
		 *
			TODO: 비즈니스 요구 조건에 맞춰서 해야 한다.
		 1) userId 를 통해 필터링 데이터를 조회한다.
		 2) 싫어요보냈던 카드는 다시 소개되지 않는다.
		 3) 이성 카드만 조회 하도록
		 */
		return cardRepository.findCardsByUserFilter(userId);
//		return cardRepository.findTop10ByOrderByCreatedAtDesc();
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
