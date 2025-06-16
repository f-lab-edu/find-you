package com.aksrua.user.data.entity;

import com.aksrua.card.data.entity.Card;
import com.aksrua.common.entity.BaseEntity;
import com.aksrua.filter.data.entity.Filter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

	//TODO: user 마지막 접속일시로 최근 접속일 기록하기
	//TODO: user 가입일자로 '최근 가입한 사람' 필터링 해주기 위한 데이터
	@Column(nullable = false)
	private LocalDateTime registeredAt;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Card card;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Filter filter;

	@QueryProjection
	public User(Gender gender) {
		this.gender = gender;
	}
}
