package com.aksrua.like.data.repository;

import com.aksrua.like.data.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

	/**
	 * 	select
	 *         l1_0.id
	 *     from
	 *         likes l1_0
	 *     where
	 *         l1_0.sender_card_id=?
	 *         and l1_0.receiver_card_id=?
	 *     limit
	 *         ?
	 */
	boolean existsBySenderCardIdAndReceiverCardId(Long senderCardId, Long receiverCardId);
}