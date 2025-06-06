package com.aksrua.user.controller.dto.request;

import com.aksrua.user.data.entity.Gender;
import com.aksrua.user.data.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

	@NotBlank(message = "이름을 입력해주세요.")
	private String username;

	@NotNull(message = "성별을 입력해주세요.")
	//TODO: enum 값 validation 해야함. 만약에 3으로 들어오면 exception 발생
	private Gender gender;

	@NotNull(message = "생년월일을 입력해주세요.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
	private LocalDate birthDate;

	@NotBlank(message = "이메일을 입력해주세요.")
	@Email
	private String email;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@NotBlank
	private String password;

	@NotBlank(message = "휴대폰 번호를 입력해주세요.")
	@NotBlank
	private String phoneNumber;

	public User toEntity() {
		return User.builder()
				.username(username)
				.gender(gender)
				.birthDate(birthDate)
				.email(email)
				.password(password)//TODO: password encode
				.phoneNumber(phoneNumber)
				.registeredAt(LocalDateTime.now())
				.build();
	}
}
