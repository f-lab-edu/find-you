package com.aksrua.like.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.like.data.entity.Like;
import com.aksrua.like.data.entity.Status;
import com.aksrua.like.data.repository.LikeRepository;
import com.aksrua.user.data.entity.Gender;
import com.aksrua.user.data.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
class LikeServiceTest {

	@InjectMocks
	private LikeService likeService;

	@Mock
	private LikeRepository likeRepository;

	User[] maleUsers = new User[5];
	User[] femaleUsers = new User[5];
	Card[] maleCards = new Card[5];
	Card[] femaleCards = new Card[5];

	@BeforeEach
	void setUp() {
		for (int i = 0; i < 5; i++) {
			maleUsers[i] = User.builder()
					.id((long) i + 1)
					.username("male" + i)
					.gender(Gender.MALE)
					.birthDate(LocalDate.parse("1992.02.14"))
					.email("male" + i + "@gmail.com")
					.password("male" + i)
					.phoneNumber("1234567890")
					.build();

			femaleUsers[i] = User.builder()
					.id((long) i + 6)
					.username("female" + i)
					.gender(Gender.FEMALE)
					.birthDate(LocalDate.parse("1992.02.14"))
					.email("female" + i + "@gmail.com")
					.password("female" + i)
					.phoneNumber("1234567890")
					.build();
		}

		for (int i = 0; i < 5; i++) {
			maleCards[i] = Card.builder()
					.user(maleUsers[i])
					.nickname("nickname" + i)
					.age(22)
					.height(180)
					.bodyType(BodyType.SLIM)
					.job("무직")
					.address("서울 특별시 은평구")
					.introduction("안녕하세요 :)")
					.imagesUrl(null)
					.religion(Religion.NONE)
					.build();

			femaleCards[i] = Card.builder()
					.user(femaleUsers[i])
					.nickname("nickname" + (i + 5))
					.age(22)
					.height(160)
					.bodyType(BodyType.SLIM)
					.job("무직")
					.address("서울 특별시 은평구")
					.introduction("안녕하세요 :)")
					.imagesUrl(null)
					.religion(Religion.NONE)
					.build();
		}
	}

	@DisplayName("senderId와 receiverId를 반대로 조회하여 기존 테이블에 없다면 단순 좋아요 생성")
	@Test
	void ifNoneExistInRepositorySendLikesWithSentStatus() {
		// given
		LocalDateTime now = LocalDateTime.now();

		Like like = Like.builder()
				.senderCardId(maleCards[0].getId())
				.receiverCardId(femaleCards[0].getId())
				.registeredAt(now)
				.status(Status.SENT)
				.build();

		given(likeRepository.existsBySenderCardIdAndReceiverCardId(maleCards[0].getId(), femaleCards[0].getId())).willReturn(false);
		given(likeRepository.save(any(Like.class))).willReturn(like);

		// when
		Like sentLike = likeService.sendLike(like);

		assertThat(sentLike).isNotNull();
		assertThat(sentLike.getSenderCardId()).isEqualTo(maleCards[0].getId());
		assertThat(sentLike.getReceiverCardId()).isEqualTo(femaleCards[0].getId());
		assertThat(sentLike.getRegisteredAt()).isEqualTo(now);
		assertThat(sentLike.getStatus()).isEqualTo(Status.SENT);

		verify(likeRepository).existsBySenderCardIdAndReceiverCardId(maleCards[0].getId(), femaleCards[0].getId());
		verify(likeRepository).save(any(Like.class));
	}

	@DisplayName("senderId와 receiverId를 반대로 조회하여 기존 테이블에 있다면 매칭으로 생성")
	@Test
	void ifExistInRepositorySendLikesWithMatchedStatus() {

	}

	@DisplayName("서로 좋아요가 되어있는 상태에서는 삭제가 불가능하다.")
	@Test
	void ifMatchedStatusImpossibleRemoveLikes() {

	}

	@DisplayName("서로 좋아요가 되어있는 상태가 아니면은 좋아요 취소로직을 실행한다.")
	@Test
	void ifNoMatchedStatusPossibleRemoveLikes() {

	}
}