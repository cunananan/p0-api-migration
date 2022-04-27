package com.revature.exceptions;

public class AuthenticationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuthenticationException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuthenticationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause, false, false);
		// TODO Auto-generated constructor stub
	}

	public AuthenticationException(String message) {
		super(message, null, false, false);
		// TODO Auto-generated constructor stub
	}

	public AuthenticationException(Throwable cause) {
		super(null, cause, false, false);
		// TODO Auto-generated constructor stub
	}
}
