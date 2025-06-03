package com.aksrua.common.exception;

import com.aksrua.common.dto.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	public ApiResponse<Object> bindException(BindException e) {
		return ApiResponse.of(
				HttpStatus.BAD_REQUEST,
				e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
				null
		);
	}
}
