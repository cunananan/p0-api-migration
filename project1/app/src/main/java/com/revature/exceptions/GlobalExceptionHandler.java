package com.revature.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.revature.controllers.UserController;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="No items were found")
	@ExceptionHandler(ItemNotFoundException.class)
	public void handleItemNotFoundException() {
//		LOG.debug("ItemNotFoundException was thrown and handled");
		LOG.info("An item query returned no results");
	}
	
	@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="No users were found")
	@ExceptionHandler(UserNotFoundException.class)
	public void handleUserNotFoundException() {
//		LOG.debug("UserNotFoundException was thrown and handled");
		LOG.info("A user query returned no results");
	}
	
	@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="User with that username or email already exists")
	@ExceptionHandler(UserAlreadyExistsException.class)
	public void handleUserAlreadyExistsException() {
//		LOG.debug("UserAlreadyExistsException was thrown and handled");
		LOG.warn("An attempt to create a user that already exists was made");
	}
	
	@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="Bad arguments were provided")
	@ExceptionHandler(ValidationException.class)
	public void handleValidationException() {
//		LOG.debug("ValidationException was thrown and handled");
		LOG.warn("Invalid arguments were passed");
	}
	
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED, reason="Could not authenticate user")
	@ExceptionHandler(AuthenticationException.class)
	public void handleAuthenticationException() {
//		LOG.debug("AuthenticationException was thrown and handled");
		LOG.warn("User authentication failed");
	}
	
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED, reason="Could not authorize user")
	@ExceptionHandler(AuthorizationException.class)
	public void handleAuthorizationException() {
//		LOG.debug("AuthorizationException was thrown and handled");
		LOG.warn("User authorization failed");
	}
	
	@ResponseStatus(code=HttpStatus.FORBIDDEN, reason="User access is not allowed")
	@ExceptionHandler(AccessDeniedException.class)
	public void handleAccessDeniedException() {
//		LOG.debug("AccessDeniedException was thrown and handled");
		LOG.warn("User tried accessing a forbidden method/endpoint");
	}
}
