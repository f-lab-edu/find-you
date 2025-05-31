package com.aksrua.card.data.repository;

import com.aksrua.card.data.entity.Card;
import java.util.List;

public interface CardRepositoryCustom {

	List<Card> findCardsByUserFilter(Long userId);
}
