package com.aksrua.common.exception;

import com.aksrua.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
				e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
				null
		);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(IllegalArgumentException.class)
	public ApiResponse<Object> handleIllegalArgumentException(IllegalArgumentException e) {
		return ApiResponse.of(
				HttpStatus.INTERNAL_SERVER_ERROR,
				e.getMessage(),
				null
		);
	}
}
