package com.revature.util;

import org.apache.commons.lang3.StringUtils;

public class ValidationUtil {
	
	public static boolean validateUsername(String value) {
		// TODO real validation
		return !StringUtils.isBlank(value);
	}
	
	public static boolean validateEmail(String value) {
		// TODO real validation
		return !StringUtils.isBlank(value);
	}
	
	public static boolean validatePassword(String value) {
		// TODO real validation
		return !StringUtils.isBlank(value);
	}
}
