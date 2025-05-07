package com.aksrua.card.service;

import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.repository.CardRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CardService {

	private final CardRepository cardRepository;

	public CardService(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	public List<Card> getCardList() {
		//TODO: userId 를 통해 필터링 데이터를 조회한다.
		return cardRepository.findTop10ByOrderByCreatedAtDesc();
	}
}
