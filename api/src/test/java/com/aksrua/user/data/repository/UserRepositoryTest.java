package com.aksrua.user.data.repository;

import static com.aksrua.user.data.entity.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

import com.aksrua.user.data.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@AfterEach
	void tearDown() {
		userRepository.deleteAllInBatch();
	}

	@DisplayName("해당 이메일이 이미 존재하는지 확인한다.")
	@Test
	void findExistEmail() {
		// given
		String findMail = "clazziquai01@gmail.com";

		User user1 = User.builder()
				.username("김만겸")
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email(findMail)
				.password("1234")
				.phoneNumber("010-7617-2221")
				.registeredAt(LocalDateTime.now())
				.build();

		userRepository.save(user1);

		// when
		Boolean existsByEmail = userRepository.existsByEmail(findMail);

		// then
		assertThat(existsByEmail).isTrue();
	}

	@DisplayName("해당 이메일이 이미 존재하지 않음을 확인한다.")
	@Test
	void findNonExistEmail() {
		// given
		String savedMail = "clazziquai01@gmail.com";
		String theOtherMail = "the-ohter@gmail.com";

		User user1 = User.builder()
				.username("김만겸")
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email(savedMail)
				.password("1234")
				.phoneNumber("010-7617-2221")
				.registeredAt(LocalDateTime.now())
				.build();

		userRepository.save(user1);

		// when
		Boolean existsByEmail = userRepository.existsByEmail(theOtherMail);

		// then
		assertThat(existsByEmail).isFalse();
	}
}