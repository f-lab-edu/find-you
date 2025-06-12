package com.aksrua.filterchain;

import com.aksrua.auth.data.entity.AuthToken;
import com.aksrua.auth.data.repository.AuthTokenRepository;
import com.aksrua.common.exception.AuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

	private final AuthTokenRepository authTokenRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("[AuthTokenFilter] 요청 필터링 중: " + request.getRequestURI());

		String token = request.getHeader("X-Auth-Token");

		if (token != null) {
			AuthToken authToken = authTokenRepository.findByToken(token)
					.filter(t -> t.getExpiredAt().isAfter(LocalDateTime.now()))
					.orElseThrow(() -> new AuthenticationException("토큰이 유효하지 않습니다."));

			request.setAttribute("userId", authToken.getUser().getId());
		}

		filterChain.doFilter(request, response);
	}
}
