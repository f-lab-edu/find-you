package com.aksrua.filter.data.entity;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.user.data.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

@ToString
@Getter
@Table(name = "CARD_FILTER")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Entity
public class Filter {

	@Id
	private Long id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private Integer minAge;

	@Column(nullable = false)
	private Integer maxAge;

	@Column(nullable = false)
	private Integer minHeight;

	@Column(nullable = false)
	private Integer maxHeight;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private BodyType bodyType;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Religion religion;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	@Builder
	public Filter(User user) {
		this.user = user;
	}

	@QueryProjection
	public Filter(Integer minAge, Integer maxAge, Integer minHeight, Integer maxHeight,
				  BodyType bodyType,
				  Religion religion) {
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.bodyType = bodyType;
		this.religion = religion;
	}
}
