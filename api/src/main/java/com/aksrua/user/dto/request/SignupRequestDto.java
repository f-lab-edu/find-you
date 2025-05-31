package com.aksrua.user.dto.request;

import com.aksrua.user.data.entity.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

	@NotBlank
	private String username;

	private Gender gender;

	@NotNull(message = "날짜는 필수입니다.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
	private LocalDate birthDate;

	@Email
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String phoneNumber;
}