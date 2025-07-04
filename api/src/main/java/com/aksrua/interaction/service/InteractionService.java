package com.aksrua.interaction.service;

import com.aksrua.card.data.entity.Card;
import com.aksrua.card.service.CardService;
import com.aksrua.coin.service.CoinService;
import com.aksrua.common.EventType;
import com.aksrua.common.outboxmessagerelay.OutboxEventPublisher;
import com.aksrua.common.payload.LikeEventPayload;
import com.aksrua.interaction.data.entity.Dislike;
import com.aksrua.interaction.data.entity.Like;
import com.aksrua.interaction.data.entity.LikeStatus;
import com.aksrua.interaction.data.repository.DislikeRepository;
import com.aksrua.interaction.data.repository.LikeRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class InteractionService {

	//TODO: validate가 너무 난잡하게 되어있다.
	private final LikeFinder likeFinder;
	private final LikeValidator likeValidator;
	private final LikeRepository likeRepository;
	private final DislikeRepository dislikeRepository;
	private final CardService cardService;
	private final CoinService coinService;

	private final OutboxEventPublisher outboxEventPublisher;

	@Transactional
	public Like sendLike(Like like) {
		// 먼저 보내는 사람의 코인이 있는지 없는지 여부 조사
		if (!coinService.hasEnoughCoins()) {
			throw new RuntimeException("코인이 없습니다.");
		}

		// 중복 좋아요, 나에게 보내는 좋아요 검증
		likeValidator.validateLikeRequest(like);

		// 상대방이 나에게 보낸 좋아요가 있는지 확인
		Optional<Like> hasLikedMe = findHasLikedMe(like);

		if (hasLikedMe.isPresent()) {
			Like updatedLike = hasLikedMe.get();
			updatedLike.changeLikeStatusToMatched();
			Like receivedLike = likeRepository.save(updatedLike);

			outboxEventPublisher.publish(
					EventType.LIKE,
					LikeEventPayload.builder()
							.likeId(receivedLike.getId())
							.senderCardId(receivedLike.getSenderCardId())
							.receiverCardId(receivedLike.getReceiverCardId())
							.likeStatus(String.valueOf(receivedLike.getLikeStatus()))
							.registeredAt(receivedLike.getRegisteredAt())
							.checkedAt(receivedLike.getCheckedAt())
							.matchedAt(receivedLike.getMatchedAt())
							.build()
			);

			like.changeLikeStatusToMatched();
		}
		Like sentLike = likeRepository.save(like);

		outboxEventPublisher.publish(
				EventType.LIKE,
				LikeEventPayload.builder()
						.likeId(sentLike.getId())
						.senderCardId(sentLike.getSenderCardId())
						.receiverCardId(sentLike.getReceiverCardId())
						.likeStatus(String.valueOf(sentLike.getLikeStatus()))
						.registeredAt(sentLike.getRegisteredAt())
						.checkedAt(sentLike.getCheckedAt())
						.matchedAt(sentLike.getMatchedAt())
						.build()
		);

		return sentLike;
	}

	private Optional<Like> findHasLikedMe(Like like) {
		return likeRepository.findBySenderCardIdAndReceiverCardId(like.getReceiverCardId(), like.getSenderCardId());
	}

	@Transactional
	public void removeLike(Long userId, Long likeId) {
		Like like = likeFinder.findLikeById(likeId);
		//TODO: 인증 인가 검증
		likeValidator.validateRemovalPermission(like, userId);
		likeRepository.deleteByIdAndSenderCardId(likeId, userId);
	}

	/**
	 * 내가 보낸 좋아요 리스트 조회 기능
	 */
	public List<Card> getSentLikesList(Long userId) {
		List<Long> receiverCardIdList = likeRepository.findReceiverCardIdsBySenderCardId(userId);
		return cardService.getCardsListByIdIn(receiverCardIdList);
	}

	/**
	 * 내가 받은 좋아요 리스트 조회 기능
	 */
	public void getReceivedLikesList() {

	}

	/**
	 * 좋아요 상세 조회 기능
	 */

	/**
	 *  싫어요 보내기
	 */
	public void sendDisLike(Dislike dislike) {

	}
}
