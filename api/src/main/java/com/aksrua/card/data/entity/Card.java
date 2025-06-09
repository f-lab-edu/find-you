package com.aksrua.card.data.entity;

import com.aksrua.common.entity.BaseEntity;
import com.aksrua.interaction.data.entity.Like;
import com.aksrua.user.data.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

	@JoinColumn(name = "sender_card_id")
	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Like> sentLikes = new ArrayList<>();

	@JoinColumn(name = "receiver_card_id")
	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Like> receivedLikes = new ArrayList<>();

	@Builder
	public Card(String nickname, LocalDate birthDate, String job, String address, String introduction,
				String imagesUrl, String hobbies, Religion religion) {
		this.nickname = nickname;
		this.age = calculateUserAge(birthDate);
		this.job = job;
		this.address = address;
		this.introduction = introduction;
		this.imagesUrl = imagesUrl;
		this.hobbies = hobbies;
		this.religion = religion;
	}

	@QueryProjection
	public Card(User user, String nickname, Integer age, Integer height, BodyType bodyType, String job, String address,
				String introduction, String imagesUrl, Religion religion) {
		this.user = user;
		this.nickname = nickname;
		this.age = age;
		this.height = height;
		this.bodyType = bodyType;
		this.job = job;
		this.address = address;
		this.introduction = introduction;
		this.imagesUrl = imagesUrl;
		this.religion = religion;
	}

	private Integer calculateUserAge(LocalDate birthDate) {
		LocalDate now = LocalDate.now();

		int age = now.getYear() - birthDate.getYear();
		if (now.getMonthValue() < birthDate.getMonthValue() ||
				(now.getMonthValue() == birthDate.getMonthValue() && now.getDayOfMonth() < birthDate.getDayOfMonth())) {
			age--;
		}
		return age;
	}
}