package com.aksrua.user.service;

import com.aksrua.common.exception.DuplicateResourceException;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.data.repository.UserRepository;
import com.aksrua.user.dto.response.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	public User signup(User user) {
		Boolean isExist = userRepository.existsByEmail(user.getEmail());

		if (isExist) {
			throw new DuplicateResourceException("이메일이 이미 존재합니다.");
		}

		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);

		if (user != null) {
			return new CustomUserDetails(user);
		}
		return null;
	}
}
