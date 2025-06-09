package com.aksrua.interaction.data.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.aksrua.interaction.data.entity.Like;
import com.aksrua.interaction.data.entity.LikeStatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@SpringBootTest
class LikeRepositoryTest {

	@Autowired
	LikeRepository likeRepository;

	@AfterEach
	void tearDown() {
		likeRepository.deleteAllInBatch();
	}

	@DisplayName("senderId와 receiverId가 있을때 이미 서로 좋아요 보낸 이력이 있는지 조회를 한다.")
	@Test
	@Sql("/data.sql")
	void existsBySenderCardIdAndReceiverCardIdAndLikeStatus_true() {
		// given
		LocalDateTime now = LocalDateTime.now();

		Long senderId = 1L;
		Long receiverId = 5L;

		Like like = Like.builder()
				.senderCardId(senderId)
				.receiverCardId(receiverId)
				.likeStatus(LikeStatus.SENT)
				.registeredAt(now)
				.build();

		likeRepository.save(like);

		// when
		boolean isExistsLike = likeRepository.existsBySenderCardIdAndReceiverCardIdAndLikeStatus(senderId, receiverId, LikeStatus.SENT);

		// then
		assertThat(isExistsLike).isTrue();
		assertThat(like.getSenderCardId()).isEqualTo(1L);
		assertThat(like.getReceiverCardId()).isEqualTo(5L);
		assertThat(like.getRegisteredAt()).isEqualTo(now);
		assertThat(like.getLikeStatus()).isEqualTo(LikeStatus.SENT);
	}

	@DisplayName("senderId와 receiverId가 있을때 이미 서로 좋아요 보낸 이력이 없는 조회를 한다.")
	@Test
	@Sql("/data.sql")
	void existsBySenderCardIdAndReceiverCardIdAndLikeStatus_false() {
		// given
		LocalDateTime now = LocalDateTime.now();

		Long senderId = 1L;
		Long receiverId = 5L;

		Long testSenderId = 2L;
		Long testReceiverId = 5L;

		Like like = Like.builder()
				.senderCardId(senderId)
				.receiverCardId(receiverId)
				.registeredAt(now)
				.likeStatus(LikeStatus.SENT)
				.build();

		likeRepository.save(like);

		// when
		boolean isExistsLike = likeRepository.existsBySenderCardIdAndReceiverCardIdAndLikeStatus(testSenderId, testReceiverId, LikeStatus.SENT);

		// then
		assertThat(isExistsLike).isFalse();
	}
}