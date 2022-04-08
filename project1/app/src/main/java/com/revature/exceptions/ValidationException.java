package com.revature.exceptions;

public class ValidationException  extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ValidationException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValidationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause, false, false);
		// TODO Auto-generated constructor stub
	}

	public ValidationException(String message) {
		super(message, null, false, false);
		// TODO Auto-generated constructor stub
	}

	public ValidationException(Throwable cause) {
		super(null, cause, false, false);
		// TODO Auto-generated constructor stub
	}
}
