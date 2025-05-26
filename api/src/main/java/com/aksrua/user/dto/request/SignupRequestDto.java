package com.aksrua.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

	@Email
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String phoneNumber;
}
