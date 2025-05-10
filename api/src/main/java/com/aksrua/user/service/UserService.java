package com.aksrua.user.service;

import com.aksrua.user.data.entity.User;
import com.aksrua.user.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User signup(User user) {
		return userRepository.save(user);
	}
}
