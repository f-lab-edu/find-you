package com.aksrua.user.controller.dto.request;

import static com.aksrua.user.data.entity.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

import com.aksrua.user.data.entity.Gender;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class SignupRequestDtoTest {

	private static ValidatorFactory factory;
	private static Validator validator;

	@BeforeAll
	public static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@DisplayName("회원가입 요청 폼 모든 값 검증 완료")
	@Test
	void inputSuccess() {
		SignupRequestDto requestDto = SignupRequestDto.builder()
				.username("김이름")
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("test@gmail.com")
				.password("1234")
				.phoneNumber("010-2345-3123")
				.build();

		Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(requestDto);

		assertThat(violations).isEmpty();
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"", " ", "\t", "\n"})
	@DisplayName("회원가입 요청 username 검증")
	void inputFailedWithUsername(String username) {
		SignupRequestDto requestDto = SignupRequestDto.builder()
				.username(username)
				.gender(MALE)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("test@gmail.com")
				.password("1234")
				.phoneNumber("010-2345-3123")
				.build();

		Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(requestDto);

		assertThat(violations).isNotEmpty();
		assertThat(violations).hasSize(1);

		ConstraintViolation<SignupRequestDto> violation = violations.iterator().next();

		assertThat(violation.getPropertyPath().toString()).isEqualTo("username");
		assertThat(violation.getMessage()).isEqualTo("이름을 입력해주세요.");
		assertThat(violation.getInvalidValue()).isEqualTo(username);
		assertThat(violation.getRootBeanClass()).isEqualTo(SignupRequestDto.class);
	}

	@ParameterizedTest
	@ValueSource(strings = {"MALE", "FEMALE"})
	@DisplayName("회원가입 요청 폼 GENDER 검증, MALE과 FEMALE값만 허용된다.")
	void inputFailedWithGender(String gender) {
		SignupRequestDto requestDto = SignupRequestDto.builder()
				.username("김이름")
				.gender(Gender.valueOf(gender))
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("test@gmail.com")
				.password("1234")
				.phoneNumber("010-2345-3123")
				.build();

		Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(requestDto);

		assertThat(violations).isEmpty();
	}

	@DisplayName("회원가입 폼 검증 gender 값에 null값은 허용되지 않는다.")
	@Test
	void inputFailedWithNullGender() {
		SignupRequestDto requestDto = SignupRequestDto.builder()
				.username("김이름")
				.gender(null)
				.birthDate(LocalDate.parse("1992-02-14"))
				.email("test@gmail.com")
				.password("1234")
				.phoneNumber("010-2345-3123")
				.build();

		Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(requestDto);

		assertThat(violations).isNotEmpty();
		assertThat(violations).hasSize(1);

		ConstraintViolation<SignupRequestDto> violation = violations.iterator().next();

		System.out.println(violation);

		assertThat(violation.getPropertyPath().toString()).isEqualTo("gender");
		assertThat(violation.getMessage()).isEqualTo("성별을 입력해주세요.");
		assertThat(violation.getInvalidValue()).isEqualTo(null);
		assertThat(violation.getRootBeanClass()).isEqualTo(SignupRequestDto.class);
	}

	@DisplayName("회원가입 요청 birthdate null, empty 값 검증")
	@Test
	void inputFailedWithBirthDate() {
		SignupRequestDto requestDto = SignupRequestDto.builder()
				.username("김이름")
				.gender(MALE)
				.birthDate(null)
				.email("test@gmail.com")
				.password("1234")
				.phoneNumber("010-2345-3123")
				.build();

		Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(requestDto);

		assertThat(violations).isNotEmpty();
		assertThat(violations).hasSize(1);

		ConstraintViolation<SignupRequestDto> violation = violations.iterator().next();

		assertThat(violation.getPropertyPath().toString()).isEqualTo("birthDate");
		assertThat(violation.getMessage()).isEqualTo("생년월일을 입력해주세요.");
		assertThat(violation.getInvalidValue()).isEqualTo(null);
		assertThat(violation.getRootBeanClass()).isEqualTo(SignupRequestDto.class);
	}
}