package com.bookLords.model.exceptions;

public class InvalidDataException extends Exception {

	private static final long serialVersionUID = -3938162429397772071L;

	public InvalidDataException() {
		super();
	}

	public InvalidDataException(String message, Exception trace) {
		super(message, trace);
	}

	public InvalidDataException(String message) {
		super(message);
	}

	public InvalidDataException(Exception trace) {
		super(trace);
	}
	
	
}
