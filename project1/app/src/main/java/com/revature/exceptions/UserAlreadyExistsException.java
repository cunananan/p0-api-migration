package com.revature.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyExistsException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public UserAlreadyExistsException(String message, Throwable cause) {
		super(message, cause, false, false);
		// TODO Auto-generated constructor stub
	}

	public UserAlreadyExistsException(String message) {
		super(message, null, false, false);
		// TODO Auto-generated constructor stub
	}

	public UserAlreadyExistsException(Throwable cause) {
		super(null, cause, false, false);
		// TODO Auto-generated constructor stub
	}
}
