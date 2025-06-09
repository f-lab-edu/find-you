package com.aksrua.interaction.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.interaction.data.entity.Like;
import com.aksrua.interaction.data.entity.LikeStatus;
import com.aksrua.interaction.data.repository.DislikeRepository;
import com.aksrua.interaction.data.repository.LikeRepository;
import com.aksrua.user.data.entity.Gender;
import com.aksrua.user.data.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class InteractionServiceTest {

	@InjectMocks
	private InteractionService interactionService;

	@Mock
	private LikeRepository likeRepository;

	@Mock
	private DislikeRepository dislikeRepository;

	User[] maleUsers = new User[5];
	User[] femaleUsers = new User[5];
	Card[] maleCards = new Card[5];
	Card[] femaleCards = new Card[5];

	@BeforeEach
	void setUp() {
		for (int i = 0; i < 5; i++) {
			maleUsers[i] = createUser((long) i + 1, "male" + i, Gender.MALE);
			femaleUsers[i] = createUser((long) i + 6, "female" + i, Gender.FEMALE);

			maleCards[i] = createCard(maleUsers[i], "nickname" + i, 22, 180);
			femaleCards[i] = createCard(femaleUsers[i], "nickname" + (i + 5), 22, 160);
		}
	}

	@DisplayName("좋아요를 보내는데 동일한 데이터가 있으면 exception을 반환한다.")
	@Test
	void duplicateSentLikeException() {

	}

	@DisplayName("싫어요를 보내는데 동일한 데이터가 있으면 exception을 반환한다.")
	@Test
	void duplicateSentDisLikeException() {

	}

	@DisplayName("senderId가 receiverId에게 좋아요를 보내기 전에 receiverId가 senderId에게 좋아요를 보냈는지 확인 후"
			+ "존재하지 않는다면 SENT와 함께 생성")
	@Test
	void sendLikeTestCase1() {
		LocalDateTime now = LocalDateTime.now();

		Like like = Like.builder()
				.senderCardId(maleCards[0].getUser().getId())
				.receiverCardId(femaleCards[0].getUser().getId())
				.registeredAt(now)
				.likeStatus(LikeStatus.SENT)
				.build();

		given(likeRepository.existsBySenderCardIdAndReceiverCardIdAndLikeStatus(
				femaleCards[0].getUser().getId(),
				maleCards[0].getUser().getId(),
				LikeStatus.SENT)).willReturn(false);
		given(likeRepository.save(any(Like.class))).willReturn(like);

		// when
		Like sentLike = interactionService.sendLike(like);

		assertThat(sentLike).isNotNull();
		assertThat(sentLike.getSenderCardId()).isEqualTo(maleCards[0].getUser().getId());
		assertThat(sentLike.getReceiverCardId()).isEqualTo(femaleCards[0].getUser().getId());
		assertThat(sentLike.getRegisteredAt()).isEqualTo(now);
		assertThat(sentLike.getLikeStatus()).isEqualTo(LikeStatus.SENT);

		verify(likeRepository).existsBySenderCardIdAndReceiverCardIdAndLikeStatus(maleCards[0].getUser().getId(), femaleCards[0].getUser().getId(), LikeStatus.SENT);
		verify(likeRepository).save(any(Like.class));
	}

	@DisplayName("senderId가 receiverId에게 좋아요를 보내기 전에 receiverId가 senderId에게 좋아요를 보냈는지 확인 후"
			+ "존재한다면 MATCHED와 함께 생성")
	@Test
	void sendLikeTestCase2() {

	}

	private User createUser(Long id, String username, Gender gender) {
		return User.builder()
				.id(id)
				.username(username)
				.gender(gender)
				.birthDate(LocalDate.of(1992, 2, 14))
				.email(username + "@gmail.com")
				.password(username)
				.phoneNumber("1234567890")
				.build();
	}

	private Card createCard(User user, String nickname, int age, int height) {
		return Card.builder()
				.user(user)
				.nickname(nickname)
				.age(age)
				.height(height)
				.bodyType(BodyType.SLIM)
				.job("무직")
				.address("서울 특별시 은평구")
				.introduction("안녕하세요 :)")
				.imagesUrl(null)
				.religion(Religion.NONE)
				.build();
	}
}