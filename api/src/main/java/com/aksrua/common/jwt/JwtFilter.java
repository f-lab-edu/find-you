package com.aksrua.common.jwt;

import com.aksrua.user.data.entity.User;
import com.aksrua.user.dto.response.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/*
 * 토큰 검증 필터
 * JWT11 로그인 검증 필터
 * */
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String accessToken = request.getHeader("Authorization");

		if (accessToken == null) {
			filterChain.doFilter(request, response);
			return;
		}

		if (jwtUtil.isExpired(accessToken)) {
			log.info(" ::: token expired");
			filterChain.doFilter(request, response);
			return;
		}

		String category = jwtUtil.getCategory(accessToken);

		if (!category.equals("CATE")) {
			PrintWriter printWriter = response.getWriter();
			printWriter.print("invalid access token");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		Long userId = jwtUtil.getUserId(accessToken);
		String username = jwtUtil.getUsername(accessToken);
		String role = jwtUtil.getRole(accessToken);

		User user = new User(userId, username, "tempPassword");

		CustomUserDetails customUserDetails = new CustomUserDetails(user);

		// security 인증 토큰 생성
		Authentication authToken = new UsernamePasswordAuthenticationToken(
				customUserDetails, null, customUserDetails.getAuthorities());

		// 세션에 사용자 등록
		SecurityContextHolder.getContext().setAuthentication(authToken);

		filterChain.doFilter(request, response);
	}
}
