package com.revature.exceptions;

public class InsertionFailureException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Failed to add new record to database!";
	}
}
