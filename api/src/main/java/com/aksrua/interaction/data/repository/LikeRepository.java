package com.aksrua.interaction.data.repository;

import com.aksrua.interaction.data.entity.Like;
import com.aksrua.interaction.data.entity.LikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
	boolean existsBySenderCardIdAndReceiverCardIdAndLikeStatus(Long senderCardId, Long receiverCardId, LikeStatus likeStatus);

}
