package com.aksrua.card.data.entity;

import com.aksrua.user.data.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card {

	@Id
	private Long id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private String gender;

	@Column(unique = true, nullable = false)
	private String nickname;

	@Column(nullable = false)
	private Integer age;

	@Column(nullable = false)
	private Integer height;

	@Column(nullable = false)
	private String bodyType;

	@Column(nullable = false)
	private String job;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String introduction;

	private double distanceKm;

	private String imagesUrl;

	private String hobbies;

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
	public Card(String gender, String nickname, Integer age, String job, String address, String introduction,
				double distanceKm, String imagesUrl, String hobbies, String religion) {
		this.gender = gender;
		this.nickname = nickname;
		this.age = age;
		this.job = job;
		this.address = address;
		this.introduction = introduction;
		this.distanceKm = distanceKm;
		this.imagesUrl = imagesUrl;
		this.hobbies = hobbies;
		this.religion = religion;
	}

	@QueryProjection
	public Card(String gender, String nickname, Integer age, Integer height, String bodyType, String job,
				String address,
				String introduction, double distanceKm, String imagesUrl, String hobbies, String religion) {
		this.gender = gender;
		this.nickname = nickname;
		this.age = age;
		this.height = height;
		this.bodyType = bodyType;
		this.job = job;
		this.address = address;
		this.introduction = introduction;
		this.distanceKm = distanceKm;
		this.imagesUrl = imagesUrl;
		this.hobbies = hobbies;
		this.religion = religion;
	}
}