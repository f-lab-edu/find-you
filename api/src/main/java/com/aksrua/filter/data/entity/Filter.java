package com.aksrua.filter.data.entity;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.common.entity.BaseEntity;
import com.aksrua.user.data.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Table(name = "CARD_FILTER")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Entity
public class Filter extends BaseEntity {

	@Id
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;

	private Integer minAge;

	private Integer maxAge;

	private Integer minHeight;

	private Integer maxHeight;

	@Enumerated(EnumType.STRING)
	private BodyType bodyType;

	@Enumerated(EnumType.STRING)
	private Religion religion;

	@Builder
	public Filter(User user) {
		this.user = user;
	}

	@QueryProjection
	public Filter(User user, Integer minAge, Integer maxAge, Integer minHeight, Integer maxHeight,
				  BodyType bodyType,
				  Religion religion) {
		this.user = user;
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.bodyType = bodyType;
		this.religion = religion;
	}

	public void updateFilter(Filter updateFilter) {
		this.user = updateFilter.getUser();
		this.minAge = updateFilter.getMinAge();
		this.maxAge = updateFilter.getMaxAge();
		this.minHeight = updateFilter.getMinHeight();
		this.maxHeight = updateFilter.getMaxHeight();
		this.bodyType = updateFilter.getBodyType();
		this.religion = updateFilter.getReligion();
	}
}
