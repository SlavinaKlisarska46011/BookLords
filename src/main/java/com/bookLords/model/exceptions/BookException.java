package com.bookLords.model.exceptions;

public class BookException extends Exception {

	private static final long serialVersionUID = -1780134050539237609L;

	public BookException() {
	}

	public BookException(String message) {
		super(message);
	}

	public BookException(Exception trace) {
		super(trace);
	}

	public BookException(String message, Exception trace) {
		super(message, trace);
	}

}
