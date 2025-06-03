package com.aksrua.card.data.entity;

import com.aksrua.common.entity.BaseEntity;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card extends BaseEntity {

	@Id
	private Long id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;

	@Column(unique = true, nullable = false)
	private String nickname;

	@Column(nullable = false)
	private Integer age;

	@Column(nullable = false)
	private Integer height;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private BodyType bodyType;

	@Column(nullable = false)
	private String job;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String introduction;

	private String imagesUrl;

	private String hobbies;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Religion religion;

	@Builder
	public Card(String nickname, Integer age, String job, String address, String introduction,
				String imagesUrl, String hobbies, Religion religion) {
		this.nickname = nickname;
		this.age = age;
		this.job = job;
		this.address = address;
		this.introduction = introduction;
		this.imagesUrl = imagesUrl;
		this.hobbies = hobbies;
		this.religion = religion;
	}

	@QueryProjection
	public Card(String nickname, Integer age, Integer height, BodyType bodyType, String job, String address,
				String introduction, String imagesUrl, String hobbies, Religion religion) {
		this.nickname = nickname;
		this.age = age;
		this.height = height;
		this.bodyType = bodyType;
		this.job = job;
		this.address = address;
		this.introduction = introduction;
		this.imagesUrl = imagesUrl;
		this.hobbies = hobbies;
		this.religion = religion;
	}
}