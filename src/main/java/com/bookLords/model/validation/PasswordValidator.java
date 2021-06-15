package com.bookLords.model.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
	private Pattern pattern;
	private Matcher matcher;

	// (?=.*[a-zA-Z]) At least one character in [a-zA-Z]
	// (?=.*\d) At least one digit.
	// .{6,} At least 6 characters.
//	At least one upper case english letter
//	At least one lower case english letter
//	At least one digit
//	At least one special character
//	Minimum 6 in length
	private static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,}$";

	public PasswordValidator() {
		pattern = Pattern.compile(PASSWORD_PATTERN);
	}

	public boolean validate(final String hex) {
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}

}

