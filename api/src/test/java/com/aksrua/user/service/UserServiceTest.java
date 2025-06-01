package com.aksrua.user.service;

import static com.aksrua.user.data.entity.Gender.FEMALE;
import static com.aksrua.user.data.entity.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.aksrua.common.exception.DuplicateResourceException;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.data.repository.UserRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@AfterEach
	void tearDown() {
		userRepository.deleteAllInBatch();
	}

	@DisplayName("유저 생성 후 해당 유저를 반환한다.")
	@Test
	void createUser() {
		// given
		User user = User.builder()
				.username("김용환")
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("clazziquai01@gmail.com")
				.password("1234")
				.phoneNumber("010-7617-2221")
				.build();

		// when
		User signupUser = userService.signup(user);

		// then
		assertThat(signupUser.getId()).isNotNull();
		assertThat(signupUser.getGender()).isEqualByComparingTo(MALE);

		assertThat(signupUser.getBirthDate()).isEqualTo(LocalDate.parse("1992-02-14"));
		assertThat(signupUser.getEmail()).isEqualTo("clazziquai01@gmail.com");
		assertThat(signupUser.getPassword()).isEqualTo("1234");
		assertThat(signupUser.getPhoneNumber()).isEqualTo("010-7617-2221");
		/*
		assertThat(signupUser)
				.extracting("birthDate", "email", "password", "phoneNumber")
				.containsExactlyInAnyOrder(
						tuple(LocalDate.parse("1992-02-14")
								, "clazziquai01@gmail.com"
								, "1234"
								,"010-7617-2221")
				);
		 */
	}

	@DisplayName("중복된 이메일 입력 시 exception을 반환한다.")
	@Test
	void createUserWithDuplicateEmail() {
		// given
		User user1 = User.builder()
				.username("김용환")
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("clazziquai01@gmail.com")
				.password("1234")
				.phoneNumber("010-7617-2221")
				.build();

		User user2 = User.builder()
				.username("다른사람")
				.gender(FEMALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("clazziquai01@gmail.com")
				.password("1234")
				.phoneNumber("010-1234-1234")
				.build();

		// when
		User signupUser1 = userService.signup(user1);

		// when & then
		DuplicateResourceException exception = assertThrows(
				DuplicateResourceException.class,
				() -> userService.signup(user2)
		);

		assertThat(exception.getMessage()).isEqualTo("이메일이 이미 존재합니다.");
	}

	@DisplayName("유저 ID를 받아 해당 유저를 조회한다.")
	@Test
	void getUserById() {
		// given
		User user = User.builder()
				.username("김용환")
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("clazziquai01@gmail.com")
				.password("1234")
				.phoneNumber("010-7617-2221")
				.build();
		User signupUser = userService.signup(user);

		// when
		/**
		 * 해당 테스트는 signup method에 의존적이니 옳지 않은 테스트인가 ?
		 */
		User userDetail = userService.getUserDetail(signupUser.getId());

		// then
		assertThat(userDetail.getId()).isNotNull();
		assertThat(userDetail.getId()).isEqualTo(1);

		assertThat(signupUser.getGender()).isEqualByComparingTo(MALE);

		assertThat(signupUser.getBirthDate()).isEqualTo(LocalDate.parse("1992-02-14"));
		assertThat(signupUser.getEmail()).isEqualTo("clazziquai01@gmail.com");
		assertThat(signupUser.getPassword()).isEqualTo("1234");
		assertThat(signupUser.getPhoneNumber()).isEqualTo("010-7617-2221");
	}

	@DisplayName("조회하지 않는 유저ID를 조회하면 Exception을 반환한다.")
	@Test
	void getUserByInvalidIdThrowsException() {
		// given
		User user = User.builder()
				.username("김용환")
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("clazziquai01@gmail.com")
				.password("1234")
				.phoneNumber("010-7617-2221")
				.build();
		User signupUser = userService.signup(user);

		/**
		 * - 해당 테스트는 signup method에 의존적이니 옳지 않은 테스트인가 ?
		 * - 옳지않은 user id임을 어떻게 알 수 있을까 ?
		 */
		// when & then
		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> userService.getUserDetail(999L)
		);
	}
}