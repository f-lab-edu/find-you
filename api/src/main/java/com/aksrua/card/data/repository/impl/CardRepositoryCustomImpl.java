package com.aksrua.card.data.repository.impl;


import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.QCard;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.card.data.repository.CardRepositoryCustom;
import com.aksrua.card.data.repository.dto.response.CardResponseDto;
import com.aksrua.filter.data.entity.Filter;
import com.aksrua.filter.data.entity.QFilter;
import com.aksrua.user.data.entity.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
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
	public List<CardResponseDto> findCardsByUserFilter(Long userId) {
		QFilter filter = QFilter.filter;
		QCard card = QCard.card;
		QUser user = QUser.user;

		Filter userFilter = queryFactory
				.selectFrom(filter)
				.where(filter.user.id.eq(userId))
				.fetchOne();

		if (userFilter == null) {
			return Collections.emptyList();
		}

		BooleanBuilder builder = new BooleanBuilder();
		builder.and(card.user.id.ne(userId))
				.and(user.gender.ne(userFilter.getUser().getGender()))
				.and(card.age.between(userFilter.getMinAge(), userFilter.getMaxAge()))
				.and(card.height.between(userFilter.getMinHeight(), userFilter.getMaxHeight()));

		if (!BodyType.ANY.equals(userFilter.getBodyType())) {
			builder.and(card.bodyType.eq(userFilter.getBodyType()));
		}

		if (!Religion.ANY.equals(userFilter.getReligion())) {
			builder.and(card.religion.eq(userFilter.getReligion()));
		}

		return queryFactory
				.select(Projections.constructor(CardResponseDto.class,
						user.id, card.nickname, card.age, card.height, card.bodyType, card.job, card.address, card.introduction, card.imagesUrl, card.religion))
				.from(card)
				.join(card.user, user)
				.where(builder)
				.orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
				.limit(10)
				.fetch();
	}
}