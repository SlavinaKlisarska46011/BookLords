package com.bookLords.model.exceptions;

public class UserException extends Exception {

	private static final long serialVersionUID = -1005638511677138548L;

	
	public UserException() {
		super();
	}

	public UserException(String message, Exception trace) {
		super(message, trace);
	}

	public UserException(String message) {
		super(message);
	}

	public UserException(Exception trace) {
		super(trace);
	}


}
