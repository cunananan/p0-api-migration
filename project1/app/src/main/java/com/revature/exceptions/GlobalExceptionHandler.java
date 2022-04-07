package com.revature.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No items were found")
	@ExceptionHandler(ItemNotFoundException.class)
	public void handleItemNotFoundException() {
		// TODO log or something
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No users were found")
	@ExceptionHandler(UserNotFoundException.class)
	public void handleUserNotFoundException() {
		// TODO log or something
	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="User with that username or email already exists")
	@ExceptionHandler(UserAlreadyExistsException.class)
	public void handleUserAlreadyExistsException() {
		// TODO log or something
	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Bad arguments were provided")
	@ExceptionHandler(ValidationException.class)
	public void handleValidationException() {
		// TODO log or something
	}
	
	@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="Could not authenticate user")
	@ExceptionHandler(AuthenticationException.class)
	public void handleAuthenticationException() {
		// TODO log or something
	}
	
	@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="Could not authorize user")
	@ExceptionHandler(AuthorizationException.class)
	public void handleAuthorizationException() {
		// TODO log or something
	}
	
	@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="User access is not allowed")
	@ExceptionHandler(AccessDeniedException.class)
	public void handleAccessDeniedException() {
		// TODO log or something
	}
}
