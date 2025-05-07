package com.aksrua.card.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private String gender;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private Integer age;

	private String job;

	private String address;

	// 소개글
	private String introduction;

	private double distanceKm;

	private String imagesUrl;

	private String hobbies;

	private String religion;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}