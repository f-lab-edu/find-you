package com.aksrua.auth.service;

import com.aksrua.auth.data.entity.AuthToken;
import com.aksrua.auth.data.repository.AuthTokenRepository;
import com.aksrua.common.exception.AuthenticationException;
import com.aksrua.common.exception.UnauthorizedAccessException;
import com.aksrua.common.util.PasswordUtil;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.data.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthTokenService {

	private final UserRepository userRepository;
	private final AuthTokenRepository authTokenRepository;

	public String login(String email, String rawPassword) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new AuthenticationException("확인되지 않는 이메일입니다."));

		if (!PasswordUtil.matches(rawPassword, user.getPassword())) {
			throw new AuthenticationException("비밀번호를 확인해주세요.");
		}

		String token = UUID.randomUUID().toString();
		AuthToken authToken = AuthToken.builder()
				.token(token)
				.user(user)
				.issuedAt(LocalDateTime.now())
				.expiredAt(LocalDateTime.now().plusMinutes(30))
				.build();

		authTokenRepository.save(authToken);
		return token;
	}

	public void logout(String token) {
		authTokenRepository.findByToken(token)
				.ifPresent(authTokenRepository::delete);
	}

	/*
	public User getUserIdFromToken(String token) {
		return authTokenRepository.findByToken(token)
				.map(AuthToken::getUser)
				.orElseThrow(() -> new UnauthorizedAccessException("유효하지 않은 토큰입니다"));
	}
	*/
}
