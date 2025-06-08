package com.aksrua.card.service;

import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.repository.CardRepository;
import com.aksrua.common.exception.DuplicateResourceException;
import com.aksrua.filter.service.FilterService;
import com.aksrua.like.data.entity.Like;
import com.aksrua.like.data.repository.LikeRepository;
import java.util.List;
import java.util.Optional;
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
	public Card createCard(Card card) {
		Boolean isExist = cardRepository.existsByNickname(card.getNickname());

		if (isExist) {
			throw new DuplicateResourceException("닉네임이 이미 존재합니다.");
		}

		return cardRepository.save(card);
	}

	/**
	 * @return Card list 10장
	 * @Desc: 특정 시간마다 보여주는 10장의 소개팅 카드
	 * 	TODO: 비즈니스 요구 조건에 맞춰서 해야 한다.
	 * 		 1) userId 를 통해 필터링 데이터를 조회한다.
	 * 		 2) 싫어요보냈던 카드는 다시 소개되지 않는다.
	 * 		 3) 이성 카드만 조회 하도록
	 */
	public List<Card> getCardList(Long userId) {
		return cardRepository.findCardsByUserFilter(userId);
	}

	public Card getCardDetails(Long cardId) {
		return cardRepository.findById(cardId)
				.orElseThrow(() -> new IllegalArgumentException("카드 정보를 찾을 수 없습니다."));
	}
}
