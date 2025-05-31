package com.aksrua.card.data.repository;
import com.aksrua.card.data.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>, CardRepositoryCustom {

	Boolean existsByNickname(String nickname);

	Card findByUserId(Long senderId);
}