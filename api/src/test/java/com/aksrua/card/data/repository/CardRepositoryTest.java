package com.aksrua.card.data.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.aksrua.card.data.entity.Card;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.data.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Slf4j
@SpringBootTest
//@DataJpaTest
class CardRepositoryTest {

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private UserRepository userRepository;

	@DisplayName("10장의 이성 카드를 소개한다")
	@Test
	void findTop10ByOrderByCreatedAtDesc() {
		// given
		List<User> userList = new ArrayList<>();
		List<Card> cardList = new ArrayList<>();

		for (int i = 0; i < 15; i++) {
			User user = User.builder()
					.username("username" + i)
					.email("email" + i + "@gmail.com")
					.phoneNumber("010-" + i)
					.build();

			userList.add(user);
		}

		/**
		 * QNA: CardRepository test에 등장한 userRepository?
		 */
		userRepository.saveAll(userList);

		for (int i = 0; i < 15; i++) {
			log.info(" ::: user info={}", userList.get(i).toString());
			Card card = Card.builder()
					.user(userList.get(i))
					.gender("MALE")
					.nickname("nickname" + i)
					.age(22 + i)
					.job("개발자")
					.address("서울시 은평구")
					.introduction("안녕하세요")
					.distanceKm(11 + i)
					.imagesUrl("imagesUrl")
					.hobbies("hobbies")
					.religion("기독교")
					.build();

			cardList.add(card);
		}
		cardRepository.saveAll(cardList);

		// when
		List<Card> findCards = cardRepository.findTop10ByOrderByCreatedAtDesc();

		// then
		assertThat(findCards).hasSize(10);
	}
}