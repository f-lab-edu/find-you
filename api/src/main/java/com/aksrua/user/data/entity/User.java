package com.aksrua.user.data.entity;

import com.aksrua.card.data.entity.Card;
import com.aksrua.common.entity.BaseEntity;
import com.aksrua.filter.data.entity.Filter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
public class User extends BaseEntity {

	//TODO: user 마지막 접속일시로 최근 접속일 기록하기
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(name = "gender", nullable = false)
//	@Enumerated(EnumType.STRING)
	private Gender gender;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
	@Column(nullable = false)
	private LocalDate birthDate;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private LocalDateTime registeredAt;

	//	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	@OneToOne(mappedBy = "user")
	private Card card;

	//	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	@OneToOne(mappedBy = "user")
	private Filter filter;

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
