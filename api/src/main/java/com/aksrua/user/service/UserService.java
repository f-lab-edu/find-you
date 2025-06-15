package com.aksrua.user.service;

import com.aksrua.common.exception.DuplicateResourceException;
import com.aksrua.common.exception.NotFoundException;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User signup(User user) {
		Boolean isExist = userRepository.existsByEmail(user.getEmail());

		if (isExist) {
			throw new DuplicateResourceException("이메일이 이미 존재합니다.");
		}

		return userRepository.save(user);
	}

	public User getUserDetails(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));
	}

	public User getReferenceById(Long userId) {
		return userRepository.getReferenceById(userId);
	}
}
