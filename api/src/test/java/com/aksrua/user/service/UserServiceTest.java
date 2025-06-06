package com.aksrua.user.service;

import static com.aksrua.user.data.entity.Gender.FEMALE;
import static com.aksrua.user.data.entity.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;


import com.aksrua.common.exception.DuplicateResourceException;
import com.aksrua.user.data.entity.User;
import com.aksrua.user.data.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	private User sampleUser1;

	@BeforeEach
	void setUp() {
		LocalDateTime registeredAt = LocalDateTime.now();
		sampleUser1 = User.builder()
				.id(1L)
				.username("김이름")
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("nonemail@zmail.com")
				.password("1234")
				.phoneNumber("010-5912-3817")
				.registeredAt(registeredAt)
				.build();
	}

	@DisplayName("유저가 정상적으로 생성이 된다.")
	@Test
	void signupUserSuccess() {
		// given
		given(userRepository.existsByEmail(sampleUser1.getEmail())).willReturn(false);
		given(userRepository.save(any(User.class))).willReturn(sampleUser1);

		// when
		User savedUser = userService.signup(sampleUser1);

		// then
		assertThat(savedUser).isNotNull();
		assertThat(savedUser)
				.extracting("id", "username", "gender", "birthDate", "email", "password", "phoneNumber", "registeredAt")
				.contains(
						sampleUser1.getId(),
						sampleUser1.getUsername(),
						sampleUser1.getGender(),
						sampleUser1.getBirthDate(),
						sampleUser1.getEmail(),
						sampleUser1.getPassword(),
						sampleUser1.getPhoneNumber(),
						sampleUser1.getRegisteredAt()
				);
		verify(userRepository).existsByEmail(sampleUser1.getEmail());
		verify(userRepository).save(sampleUser1);
	}

	@DisplayName("유저 정보 중 이메일이 중복이면 생성이 되지 않고, 예외를 반환한다.")
	@Test
	void signupUserFailureWithDuplicateEmail() {
		// given
		given(userRepository.existsByEmail(sampleUser1.getEmail())).willReturn(true);

		// when & then
		assertThatThrownBy(() -> userService.signup(sampleUser1))
				.isInstanceOf(DuplicateResourceException.class)
				.hasMessage("이메일이 이미 존재합니다.");

		verify(userRepository).existsByEmail(sampleUser1.getEmail());
		verify(userRepository, never()).save(any(User.class));
	}

	@DisplayName("유저의 정보를 가져온다.")
	@Test
	void getUserDetail() {
		// given
		Long userId = 1L;
		given(userRepository.findById(userId)).willReturn(Optional.of(sampleUser1));

		// when
		User findUser = userService.getUserDetail(userId);

		// then
		assertThat(findUser).isNotNull();
		assertThat(findUser)
				.extracting("id", "username", "gender", "birthDate", "email", "password", "phoneNumber", "registeredAt")
				.contains(
						userId,
						sampleUser1.getUsername(),
						sampleUser1.getGender(),
						sampleUser1.getBirthDate(),
						sampleUser1.getEmail(),
						sampleUser1.getPassword(),
						sampleUser1.getPhoneNumber(),
						sampleUser1.getRegisteredAt()
				);

		verify(userRepository).findById(userId);
	}

	@DisplayName("존재하지 않는 유저의 정보를 가져오기 시도하면, 예외를 반환한다.")
	@Test
	void tryGetNonExistingUser() {
		// given
		Long userId = 999L;
		given(userRepository.findById(userId)).willReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> userService.getUserDetail(userId))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("회원 정보를 찾을 수 없습니다.");

		verify(userRepository).findById(userId);
	}
}