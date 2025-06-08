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
