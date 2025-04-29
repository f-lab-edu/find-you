package com.aksrua.card.service;

import com.aksrua.card.dto.response.CardResponseDto;
import com.aksrua.card.repository.CardRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class CardService {

	private final CardRepository cardRepository;

	public CardService(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	public List<CardResponseDto> getCardList() {
		return cardRepository.getCardList()
				.stream()
				.map(card -> CardResponseDto.builder()
						.id(card.getId())
						.nickname(card.getNickname())
						.age(card.getAge())
						.gender(card.getGender())
						.distanceKm(card.getDistanceKm())
						.imagesUrl(card.getImagesUrl())
						.hobbies(card.getHobbies())
						.build())
				.collect(Collectors.toList());
	}
}
