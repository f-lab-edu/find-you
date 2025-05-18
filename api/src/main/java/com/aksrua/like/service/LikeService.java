package com.aksrua.like.service;

import com.aksrua.like.data.entity.Like;
import com.aksrua.like.data.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeService {

	private final LikeRepository likeRepository;

	public Like likeSend(Like like) {
		return likeRepository.save(like);
	}

	public void removeLike(Long likeId) {
		likeRepository.deleteById(likeId);
	}
}
