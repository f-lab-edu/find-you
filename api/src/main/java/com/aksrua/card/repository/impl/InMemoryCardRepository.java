package com.aksrua.card.repository.impl;

import com.aksrua.card.repository.CardRepository;
import com.aksrua.card.vo.Card;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCardRepository implements CardRepository {
	private final Map<Long, Card> store = new HashMap<>();
	private long seq = 1L;

	InMemoryCardRepository() {
		List<String> imagesUrl = new ArrayList<>();
		imagesUrl.add("sampleUrl1.jpg");
		imagesUrl.add("sampleUrl2.jpg");
		imagesUrl.add("sampleUrl3.jpg");

		List<String> hobbies = new ArrayList<>();
		hobbies.add("독서");
		hobbies.add("코딩");
		hobbies.add("음악감상");

		for (int i = 0; i < 20; i++) {
			Card card = Card.builder()
					.id(seq)
					.gender("F")
					.nickname("sample" + seq++)
					.age(27)
					.job("디자이너")
					.address("서울시 은평구")
					.distanceKm(23)
					.imagesUrl(imagesUrl)
					.hobbies(hobbies)
					.build();
			store.put(seq, card);
		}
	}

	@Override
	public List<Card> getCardList() {
		return store.values().stream()
				.limit(10)
				.collect(Collectors.toList());
	}
}
