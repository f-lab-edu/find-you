package com.aksrua.auth.data.entity;

import com.aksrua.common.entity.BaseEntity;
import com.aksrua.user.data.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class AuthToken extends BaseEntity {

	@Id
	private String token;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	private LocalDateTime issuedAt;

	private LocalDateTime expiredAt;
}
