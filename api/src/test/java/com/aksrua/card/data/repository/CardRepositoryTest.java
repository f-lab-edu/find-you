package com.aksrua.card.data.repository;

import static com.aksrua.user.data.entity.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.data.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
class CardRepositoryTest {

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private UserRepository userRepository;

	@AfterEach
	void tearDown() {
		cardRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
	}

	@Transactional
	@DisplayName("해당 닉네임이 이미 존재하는지 확인한다.")
	@Test
	void findExistNickname() {
		// given
		String findNickname = "귀요미닉네임";

		User user = User.builder()
				.username("김만겸")
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("clazziquai01@naver.com")
				.password("1234")
				.phoneNumber("010-7617-2221")
				.registeredAt(LocalDateTime.now())
				.build();

		User savedUser = userRepository.save(user);

		Card card = Card.builder()
				.user(savedUser)
				.nickname(findNickname)
				.age(22)
				.height(180)
				.bodyType(BodyType.SLIM)
				.job("무직")
				.address("서울특별시 은평구")
				.introduction("안녕하세요 :)")
				.religion(Religion.NONE)
				.build();

		Card savedCard = cardRepository.save(card);

		// when
		Boolean existsByNickname = cardRepository.existsByNickname(findNickname);

		// then
		assertThat(existsByNickname).isTrue();
	}

	@Transactional
	@DisplayName("해당 닉네임이 이미 존재하지 않음을 확인한다.")
	@Test
	void findNonExistNickname() {
		// given
		String savedNickname = "귀요미닉네임";
		String theOtherNickname = "아무거나닉넴";

		User user = User.builder()
				.username("김만겸")
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("clazziquai01@naver.com")
				.password("1234")
				.phoneNumber("010-7617-2221")
				.registeredAt(LocalDateTime.now())
				.build();

		User savedUser = userRepository.save(user);

		Card card = Card.builder()
				.user(savedUser)
				.nickname(savedNickname)
				.age(22)
				.height(180)
				.bodyType(BodyType.SLIM)
				.job("무직")
				.address("서울특별시 은평구")
				.introduction("안녕하세요 :)")
				.religion(Religion.NONE)
				.build();

		Card savedCard = cardRepository.save(card);

		// when
		Boolean existsByNickname = cardRepository.existsByNickname(theOtherNickname);

		// then
		assertThat(existsByNickname).isFalse();
	}

	@DisplayName("10장을 가져오는지 확인한다")
	@Test
	//TODO: user와 card생성 후 테스트 코드 작성
	void getCardListTest() {
		// given

		// when

		// then
		assertThat(1).isEqualTo(1);
	}
}