package com.aksrua.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.aksrua.user.data.entity.User;
import com.aksrua.user.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Test
	void createUser() {
		// given
		User user = User.builder()
				.id(1L)
				.username("김용환")
				.email("clazziquai01@gmail.com")
				.phoneNumber("010-7617-2221")
				.build();

		when(userRepository.save(any(User.class))).thenReturn(user);

		// when
		User signupUser = userService.signup(user);

		// then
		assertEquals(user.getId(), signupUser.getId());
		assertEquals(user.getUsername(), signupUser.getUsername());
		assertEquals(user.getEmail(), signupUser.getEmail());
		assertEquals(user.getPhoneNumber(), signupUser.getPhoneNumber());
	}

}