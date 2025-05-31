package com.aksrua.filter.data.entity;

import com.aksrua.user.data.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

	@Column(nullable = false, columnDefinition = "INT DEFAULT 20")
	private Integer minAge;

	@Column(nullable = false, columnDefinition = "INT DEFAULT 40")
	private Integer maxAge;

	@Column(nullable = false, columnDefinition = "INT DEFAULT 150")
	private Integer minHeight;

	@Column(nullable = false, columnDefinition = "INT DEFAULT 180")
	private Integer maxHeight;

	@Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'NORMAL'")
	private String bodyType; //TODO: enum

	@Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'NONE'")
	private String religion;

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
				  String bodyType,
				  String religion) {
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.bodyType = bodyType;
		this.religion = religion;
	}
}
