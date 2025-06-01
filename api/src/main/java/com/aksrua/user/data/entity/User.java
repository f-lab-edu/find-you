package com.aksrua.user.data.entity;

import com.aksrua.card.data.entity.Card;
import com.aksrua.filter.data.entity.Filter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(nullable = false)
	private LocalDate birthDate;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String phoneNumber;

	//	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	@OneToOne(mappedBy = "user")
	private Card card;

	//	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	@OneToOne(mappedBy = "user")
	private Filter filter;

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
	public User(Long id, String username, String email, String phoneNumber) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	@Builder
	public User(Long id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}

	@Builder
	public User(String username, String email, String phoneNumber) {
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	@QueryProjection
	public User(Gender gender) {
		this.gender = gender;
	}
}
