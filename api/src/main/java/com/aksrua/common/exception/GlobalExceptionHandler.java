package com.aksrua.common.exception;

import com.aksrua.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DuplicateResourceException.class)
	public ApiResponse<String> handleUserAlreadyExists(DuplicateResourceException e) {
		return ApiResponse.of(
				HttpStatus.CONFLICT,
				e.getMessage(),
				null
		);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ApiResponse<Object> handleDtoValidation(BindException e) {
		return ApiResponse.of(
				HttpStatus.BAD_REQUEST,
				e.getBindingResult().getAllErrors().getFirst().getDefaultMessage(),
				null
		);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public ApiResponse<String> handleIllegalArgumentException(IllegalArgumentException e) {
		return ApiResponse.of(
				HttpStatus.BAD_REQUEST,
				e.getMessage(),
				null
		);
	}

	/**
	 * @desc: filter에서 authentication은 처리를 해서 필요 없을수도있다.
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AuthenticationException.class)
	public ApiResponse<String> handleAuthenticationException(AuthenticationException e) {
		log.info("AuthenticationException", e);
		return ApiResponse.of(
				HttpStatus.UNAUTHORIZED,
				e.getMessage(),
				null
		);
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(UnauthorizedAccessException.class)
	public ApiResponse<String> handleUnAuthAccessException(UnauthorizedAccessException e) {
		return ApiResponse.of(
				HttpStatus.FORBIDDEN,
				e.getMessage(),
				null
		);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ApiResponse<String> handleNotFoundException(NotFoundException e) {
		return ApiResponse.of(
				HttpStatus.NOT_FOUND,
				e.getMessage(),
				null
		);
	}
}
