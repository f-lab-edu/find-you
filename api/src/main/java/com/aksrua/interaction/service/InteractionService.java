package com.aksrua.interaction.service;

import com.aksrua.interaction.data.entity.Like;
import com.aksrua.interaction.data.repository.DislikeRepository;
import com.aksrua.interaction.data.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InteractionService {

	private final LikeRepository likeRepository;
	private final DislikeRepository dislikeRepository;


	public Like sendLike(Like like) {
		// s_id, r_id를 조회하여 반대 상황이 있다면은
		// 각기 다르게 매칭 정보까지도 넣어줘야한다.
		return likeRepository.save(like);
	}

	public void removeLike(Long likeId) {
		// 매칭이 되어있으면 취소가 불가능하고 match 로직에서 삭제해야한다.
		likeRepository.deleteById(likeId);
	}
}
