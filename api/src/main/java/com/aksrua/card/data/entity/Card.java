package com.aksrua.card.data.entity;

import com.aksrua.like.data.entity.Like;
import com.aksrua.user.data.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
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
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private String gender;

	@Column(unique = true, nullable = false)
	private String nickname;

	@Column(nullable = false)
	private Integer age;

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

	@Builder.Default
	@OneToMany(mappedBy = "senderCard")
	private List<Like> sentLikes = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "receiverCard")
	private List<Like> receivedLikes = new ArrayList<>();

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
	public Card(User user, String gender, String nickname, Integer age, String job, String address, String introduction,
				double distanceKm, String imagesUrl, String hobbies, String religion) {
		this.user = user;
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
}