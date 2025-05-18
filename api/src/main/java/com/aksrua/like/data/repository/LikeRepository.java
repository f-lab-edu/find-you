package com.aksrua.like.data.repository;

import com.aksrua.like.data.entity.Like;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

	List<Like> findBySenderCardId(Long cardId);

	List<Like> findByReceiverCardId(Long cardId);
}
