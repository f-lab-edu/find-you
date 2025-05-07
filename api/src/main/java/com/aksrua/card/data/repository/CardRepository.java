package com.aksrua.card.data.repository;

import com.aksrua.card.data.entity.Card;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CardRepository extends JpaRepository<Card, Long> {
	List<Card> findTop10ByOrderByCreatedAtDesc();
}
