package com.revature.util;

import io.micrometer.core.instrument.util.StringUtils;

public class ValidationUtil {
	
	public static boolean validateUsername(String value) {
		// TODO real validation
		return value != null && !StringUtils.isBlank(value);
	}
	
	public static boolean validateEmail(String value) {
		// TODO real validation
		return value != null && !StringUtils.isBlank(value);
	}
	
	public static boolean validatePassword(String value) {
		// TODO real validation
		return value != null && !StringUtils.isBlank(value);
	}
}
