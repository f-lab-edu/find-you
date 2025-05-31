package com.aksrua.user.data.entity;

import com.aksrua.card.data.entity.Card;
import com.aksrua.filter.data.entity.Filter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String phoneNumber;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private Card card;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
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
}
