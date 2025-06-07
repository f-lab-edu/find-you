package com.aksrua.card.data.repository.impl;

import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.entity.QCard;
import com.aksrua.card.data.repository.CardRepositoryCustom;
import com.aksrua.filter.data.entity.Filter;
import com.aksrua.filter.data.entity.QFilter;
import com.aksrua.user.data.entity.Gender;
import com.aksrua.user.data.entity.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
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
		QUser user = QUser.user;

		Gender userGender = queryFactory
				.select(user.gender)
				.from(user)
				.where(user.id.eq(userId))
				.fetchOne();

		Filter userFilter = queryFactory
				.selectFrom(filter)
				.where(filter.user.id.eq(userId))
				.fetchOne();

		BooleanBuilder builder = new BooleanBuilder();
		builder.and(card.user.id.ne(userId))
				.and(user.gender.ne(userGender));

		if (userFilter != null) {
			if (userFilter.getMinAge() != null && userFilter.getMaxAge() != null) {
				builder.and(card.age.between(userFilter.getMinAge(), userFilter.getMaxAge()));
			}

			if (userFilter.getMinHeight() != null && userFilter.getMaxHeight() != null) {
				builder.and(card.height.between(userFilter.getMinHeight(), userFilter.getMaxHeight()));
			}

			if (userFilter.getBodyType() != null) {
				builder.and(card.bodyType.eq(userFilter.getBodyType()));
			}

			if (userFilter.getReligion() != null) {
				builder.and(card.religion.eq(userFilter.getReligion()));
			}
		}

		return queryFactory
				.select(Projections.constructor(Card.class,
						card.user,
						card.nickname,
						card.age,
						card.height,
						card.bodyType,
						card.job,
						card.address,
						card.introduction,
						card.imagesUrl,
						card.religion))
				.from(card)
				.join(card.user, user)
				.where(builder)
				.orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
				.limit(10)
				.fetch();
	}
}