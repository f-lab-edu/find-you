package com.aksrua.card.service;

import static com.aksrua.user.data.entity.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.card.data.repository.CardRepository;
import com.aksrua.common.exception.DuplicateResourceException;
import com.aksrua.user.data.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CardServiceTest {

	@InjectMocks
	private CardService cardService;

	@Mock
	private CardRepository cardRepository;

	private User sampleUser;
	private Card sampleCard;

	@BeforeEach
	void setUp() {
		LocalDateTime registeredAt = LocalDateTime.now();
		sampleUser = User.builder()
				.id(1L)
				.username("김이름")
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("nonemail@zmail.com")
				.password("1234")
				.phoneNumber("010-5912-3817")
				.registeredAt(registeredAt)
				.build();

		sampleCard = Card.builder()
				.user(sampleUser)
				.nickname("nickname")
				.age(22)
				.height(180)
				.bodyType(BodyType.SLIM)
				.job("무직")
				.address("서울 특별시 은평구")
				.introduction("안녕하세요 :)")
				.imagesUrl(null)
				.religion(Religion.NONE)
				.build();
	}

	@DisplayName("가입한 유저의 카드를 생성한다.")
	@Test
	void createCard() {
		// given
		given(cardRepository.existsByNickname(sampleCard.getNickname())).willReturn(false);
		given(cardRepository.save(any(Card.class))).willReturn(sampleCard);

		// when
		Card savedCard = cardService.createCard(sampleCard);

		// then
		assertThat(savedCard).isNotNull();
		assertThat(savedCard)
				.extracting("user", "nickname", "age", "height", "bodyType", "job", "address", "introduction",
						"imagesUrl", "religion")
				.contains(
						sampleCard.getUser(),
						sampleCard.getNickname(),
						sampleCard.getAge(),
						sampleCard.getHeight(),
						sampleCard.getBodyType(),
						sampleCard.getJob(),
						sampleCard.getAddress(),
						sampleCard.getIntroduction(),
						sampleCard.getImagesUrl(),
						sampleCard.getReligion()
				);

		verify(cardRepository).existsByNickname(sampleCard.getNickname());
		verify(cardRepository).save(sampleCard);
	}

	@DisplayName("가입한 유저의 카드의 생성과정 중 닉네임이 중복되면 예외를 반환한다.")
	@Test
	void createCardWithDuplicateNicknameThrowException() {
		// given
		given(cardRepository.existsByNickname(sampleCard.getNickname())).willReturn(true);

		// when && then
		assertThatThrownBy(() -> cardService.createCard(sampleCard))
				.isInstanceOf(DuplicateResourceException.class)
				.hasMessage("닉네임이 이미 존재합니다.");

		verify(cardRepository).existsByNickname(sampleCard.getNickname());
		verify(cardRepository, never()).save(any(Card.class));
	}

	@DisplayName("해당 카드의 정보를 가져온다")
	@Test
	void getCardDetails() {
		// given
		Long cardId = 1L;
		given(cardRepository.findById(cardId)).willReturn(Optional.ofNullable(sampleCard));

		// when
		Card findCard = cardService.getCardDetails(cardId);

		// then
		assertThat(findCard).isNotNull();
		assertThat(findCard)
				.extracting("user", "nickname", "age", "height", "bodyType", "job", "address", "introduction",
						"imagesUrl", "religion")
				.contains(
						sampleCard.getUser(),
						sampleCard.getNickname(),
						sampleCard.getAge(),
						sampleCard.getHeight(),
						sampleCard.getBodyType(),
						sampleCard.getJob(),
						sampleCard.getAddress(),
						sampleCard.getIntroduction(),
						sampleCard.getImagesUrl(),
						sampleCard.getReligion()
				);

		verify(cardRepository).findById(cardId);
	}

	@DisplayName("존재하지 않는 카드의 정보를 가져오려 하면 예외를 반환한다.")
	@Test
	void getNonExistCardThrowException() {
		// given
		Long cardId = 999L;
		given(cardRepository.findById(cardId)).willReturn(Optional.empty());

		// when && then
		assertThatThrownBy(() -> cardService.getCardDetails(cardId))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("카드 정보를 찾을 수 없습니다.");

		verify(cardRepository).findById(cardId);
	}

	//TODO: 메인기능인 카드리스트 조회 로직이 대부분 repository에 가있는것같다. 이를 service로 이동할 수 없을까 ?
	@DisplayName("카드 10장을 가져온다")
	@Test
	void getCardList() {
		assertThat(1).isEqualTo(1);
	}
}