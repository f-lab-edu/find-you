package com.aksrua.card.data.repository.impl;

import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.entity.QCard;
import com.aksrua.card.data.repository.CardRepositoryCustom;
import com.aksrua.filter.data.entity.Filter;
import com.aksrua.filter.data.entity.QFilter;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CardRepositoryCustomImpl implements CardRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public CardRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<Card> findCardsByUserFilter(Long userId) {
		QFilter filter = QFilter.filter;
		QCard card = QCard.card;

		Filter userFilter = queryFactory
				.selectFrom(filter)
				.where(filter.user.id.eq(userId))
				.fetchOne();

		if (userFilter == null) {
			return Collections.emptyList();
		}

		return queryFactory
				.selectFrom(card)
				.where(
						card.user.id.ne(userId),
						card.age.between(userFilter.getMinAge(), userFilter.getMaxAge()),
						card.height.between(userFilter.getMinHeight(), userFilter.getMaxHeight()),
						card.bodyType.eq(userFilter.getBodyType()),
						card.religion.eq(userFilter.getReligion())
				)
				.fetch();
	}
}