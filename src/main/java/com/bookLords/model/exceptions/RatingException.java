package com.bookLords.model.exceptions;

public class RatingException extends Exception {

	private static final long serialVersionUID = 3552528997904453746L;

	public RatingException() {
	}

	public RatingException(String message) {
		super(message);
	}

	public RatingException(Exception trace) {
		super(trace);
	}

	public RatingException(String message, Exception trace) {
		super(message, trace);
	}

}
