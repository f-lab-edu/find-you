package com.aksrua.like.data.repository;

import com.aksrua.like.data.entity.Like;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

	/**
	 * @param senderId
	 * @return 내가 보낸 좋아요 목록
	 */
	List<Like> findBySenderCardId(Long senderId);

	/**
	 *
	 * @param receiverId
	 * @return 내가 받은 좋아요 목록
	 */
	List<Like> findByReceiverCardId(Long receiverId);

}
