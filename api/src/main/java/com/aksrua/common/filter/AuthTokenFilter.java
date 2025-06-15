package com.aksrua.common.filter;

import com.aksrua.auth.data.entity.AuthToken;
import com.aksrua.auth.data.repository.AuthTokenRepository;
import com.aksrua.common.dto.ApiResponse;
import com.aksrua.common.exception.AuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

	private static final String[] EXCLUDE_PATHS = {
			"/api/v1/users",
			"/api/v1/login"
	};
	private static final int TOKEN_EXTENSION_MINUTES = 30;
	private static final int UPDATE_THRESHOLD_MINUTES = 5;

	private final AuthTokenRepository authTokenRepository;
	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestURI = request.getRequestURI();
		log.info("request uri = {}", requestURI);

		for (String excludePath : EXCLUDE_PATHS) {
			if (requestURI.equals(excludePath) || requestURI.startsWith(excludePath + "/")) {
				filterChain.doFilter(request, response);
				return;
			}
		}

		String token = request.getHeader("X-Auth-Token");

		if (token == null) {
			/**
			 *  TODO: re-login 요청
			 */
			sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "인증 토큰이 필요합니다.");
			return;
		}

		Optional<AuthToken> authTokenOpt = authTokenRepository.findByToken(token)
				.filter(t -> t.getExpiredAt().isAfter(LocalDateTime.now()));

		if (authTokenOpt.isEmpty()) {
			sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다.");
			return;
		}

		AuthToken authToken = authTokenOpt.get();
		extendTokenIfNeeded(authToken);

		request.setAttribute("userId", authToken.getUser().getId());
		filterChain.doFilter(request, response);
	}

	private void extendTokenIfNeeded(AuthToken authToken) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime shouldUpdateAfter = authToken.getExpiredAt().minusMinutes(TOKEN_EXTENSION_MINUTES - UPDATE_THRESHOLD_MINUTES);

		// 현재 시간이 "만료시간 - 25분" 이후라면 연장 (5분 전에 한 번만 연장)
		if (now.isAfter(shouldUpdateAfter)) {
			authToken.updateExpiredAt(TOKEN_EXTENSION_MINUTES);
			authTokenRepository.save(authToken);
			log.debug("토큰 만료시간 연장: {}", authToken.getToken());
		}
	}

	private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message)
			throws IOException {
		response.setStatus(status.value());
		response.setContentType("application/json;charset=UTF-8");

		ApiResponse<String> errorResponse = ApiResponse.of(status, message, null);
		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
	}
}
