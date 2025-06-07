package com.aksrua.card.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import com.aksrua.card.data.entity.BodyType;
import com.aksrua.card.data.entity.Card;
import com.aksrua.card.data.entity.Religion;
import com.aksrua.user.data.entity.Gender;
import com.aksrua.user.data.entity.User;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateCardRequestDtoTest {

	private static ValidatorFactory factory;
	private static Validator validator;

	@BeforeAll
	public static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@DisplayName("LocalDate타입을 받아 만 나이 계산을 수행")
	@Test
	void calculateUserAge1() {
		// given
		LocalDate birthDate = LocalDate.of(1992, 2, 14);

		User user = User.builder()
				.id(1L)
				.username("aksrua")
				.gender(Gender.MALE)
				.birthDate(birthDate)
				.email("ccc@gmail.com")
				.password("1234")
				.phoneNumber("010-1234-1234")
				.registeredAt(LocalDateTime.now())
				.build();

		CreateCardRequestDto request = CreateCardRequestDto.builder()
				.userId(1L)
				.nickname("nick")
//				.age()
				.height(180)
				.bodyType(BodyType.SLIM)
				.job("none")
				.address("서울")
				.introduction("hihihi")
				.religion(Religion.NONE)
				.build();

		// when
		Card card = request.toEntity(user);

		// then
		assertThat(card.getAge()).isEqualTo(33);
	}

	@DisplayName("LocalDate타입을 받아 만 나이 계산을 수행")
	@Test
	void calculateUserAge2() {
		// given
		LocalDate birthDate = LocalDate.of(1995, 6, 7);

		User user = User.builder()
				.id(1L)
				.username("aksrua")
				.gender(Gender.MALE)
				.birthDate(birthDate)
				.email("ccc@gmail.com")
				.password("1234")
				.phoneNumber("010-1234-1234")
				.registeredAt(LocalDateTime.now())
				.build();

		CreateCardRequestDto request = CreateCardRequestDto.builder()
				.userId(1L)
				.nickname("nick")
//				.age()
				.height(180)
				.bodyType(BodyType.SLIM)
				.job("none")
				.address("서울")
				.introduction("hihihi")
				.religion(Religion.NONE)
				.build();

		// when
		Card card = request.toEntity(user);

		// then
		assertThat(card.getAge()).isEqualTo(30);
	}
}