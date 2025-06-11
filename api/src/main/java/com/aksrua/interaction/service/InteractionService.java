package com.aksrua.interaction.service;

import com.aksrua.interaction.data.entity.Dislike;
import com.aksrua.interaction.data.entity.Like;
import com.aksrua.interaction.data.entity.LikeStatus;
import com.aksrua.interaction.data.repository.DislikeRepository;
import com.aksrua.interaction.data.repository.LikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class InteractionService {

	private final LikeFinder likeFinder;
	private final LikeValidator likeValidator;
	private final LikeRepository likeRepository;
	private final DislikeRepository dislikeRepository;

	/**
	 * 조회 기능
	 */

	@Transactional
	public Like sendLike(Like like) {
		// 중복 좋아요, 나에게 보내는 좋아요 검증
		likeValidator.validateLikeRequest(like);

		// 상대방이 나에게 보낸 좋아요가 있는지 확인
		Optional<Like> hasLikedMe = findHasLikedMe(like);

		if (hasLikedMe.isPresent()) {
			Like updatedLike = hasLikedMe.get();
			updatedLike.changeLikeStatusToMatched();
			likeRepository.save(updatedLike);

			like.changeLikeStatusToMatched();
		}

		return likeRepository.save(like);
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
	 *  싫어요 보내기
	 */
}
