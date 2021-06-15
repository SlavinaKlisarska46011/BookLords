package com.bookLords.model.exceptions;

public class AuthorException extends Exception {

	private static final long serialVersionUID = 1L;

	public AuthorException() {
		super();
	}

	public AuthorException(String message, Exception trace) {
		super(message, trace);
	}

	public AuthorException(String message) {
		super(message);
	}

	public AuthorException(Exception trace) {
		super(trace);
	}

}
