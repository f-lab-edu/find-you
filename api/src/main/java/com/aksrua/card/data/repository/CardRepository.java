package com.aksrua.card.data.repository;

import com.aksrua.card.data.entity.Card;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>, CardRepositoryCustom {

	/**
	 * @return Card list 10장
	 * @Desc: 특정 시간마다 보여주는 10장의 소개팅 카드
	 * TODO
	 * i) 이성만을 조회해야한다.
	 * ii) user가 갖고있는 필터링을 조회하여 상대 카드를 조회한다.
	 *
	 * 1) 한방 쿼리를 작성한다.
	 * 2) 적당한 데이터들을 가져와서 코드로 필터링한다.
	 */
	Boolean existsByNickname(String nickname);

	Card findByUserId(Long senderId);
}
