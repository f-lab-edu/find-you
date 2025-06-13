package com.aksrua.interaction.service;

import com.aksrua.interaction.data.entity.Like;
import com.aksrua.interaction.data.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LikeFinder {

	private final LikeRepository likeRepository;

	public Like findLikeById(Long likeId) {
		return likeRepository.findById(likeId)
				.orElseThrow(() -> new IllegalArgumentException("해당 좋아요를 찾을 수 없습니다."));
	}
}
