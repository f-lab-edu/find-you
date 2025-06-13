package com.aksrua.interaction.service;

import com.aksrua.common.exception.DuplicateResourceException;
import com.aksrua.interaction.data.entity.Like;
import com.aksrua.interaction.data.repository.LikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LikeValidator {

	private final LikeRepository likeRepository;

	public void validateLikeRequest(Like like) {
		validateNotDuplicate(like);
		validateNotSelfLike(like);
	}

	private void validateNotDuplicate(Like like) {
		boolean hasLiked = likeRepository.existsBySenderCardIdAndReceiverCardId(
				like.getSenderCardId(), like.getReceiverCardId());

		if (hasLiked) {
			throw new DuplicateResourceException("이미 상대방에게 좋아요를 보냈습니다.");
		}
	}

	private void validateNotSelfLike(Like like) {
		if (like.getSenderCardId().equals(like.getReceiverCardId())) {
			throw new IllegalArgumentException("자신에게는 좋아요를 보낼 수 없습니다.");
		}
	}

	/*public void validateLikeExists(Optional<Like> like) {
		if (like.isEmpty()) {
			throw new IllegalArgumentException("해당 좋아요를 찾을 수 없습니다.");
		}
	}*/

	public void validateRemovalPermission(Like like, Long userId) {
		like.validateRemovalPermission(userId);
	}

}
