package com.aksrua.config.security;

import com.aksrua.common.jwt.JwtFilter;
import com.aksrua.common.jwt.JwtUtil;
import com.aksrua.common.jwt.LoginFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;
	private final JwtUtil jwtUtil;

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.cors((cors) -> cors
						.configurationSource(new CorsConfigurationSource() {
							@Override
							public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
								CorsConfiguration corsConfiguration = new CorsConfiguration();
								corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
								corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
								corsConfiguration.setAllowCredentials(true);
								corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
								corsConfiguration.setMaxAge(3600L);

								corsConfiguration.setExposedHeaders(Collections.singletonList("Authorization"));
								return corsConfiguration;
							}
						}));

		//csrf disable
		httpSecurity
				.csrf((auth) -> auth.disable());

		//form 로그인 방식 disable
		httpSecurity
				.formLogin((auth) -> auth.disable());

		//http basic 방식 인증 방식 disable
		httpSecurity
				.httpBasic((auth) -> auth.disable());

		//인가작업
		httpSecurity
				.authorizeHttpRequests((auth) -> auth
						.requestMatchers("/api/v1/users", "/api/v1/login").permitAll()
						.requestMatchers("/api/v1/cards/**").authenticated()           // 명시적 설정

//						.requestMatchers("/admin").hasRole("ADMIN")
						.requestMatchers("/reissue").permitAll()
						.anyRequest().authenticated());

		httpSecurity
				.addFilterAt(new LoginFilter(authenticationManagerBean(authenticationConfiguration), jwtUtil),
						UsernamePasswordAuthenticationFilter.class);

		httpSecurity
				.addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);

		//세션 설정
		httpSecurity
				.sessionManagement((session) -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return httpSecurity.build();
	}
}

/**
 * jwt를 왜 사용하는지
 * STATELESS 상태에 초점
 */