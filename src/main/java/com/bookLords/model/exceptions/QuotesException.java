package com.bookLords.model.exceptions;

public class QuotesException extends Exception {

	private static final long serialVersionUID = 4923636791166661116L;

	public QuotesException() {
		super();
	}

	public QuotesException(String message, Exception trace) {
		super(message, trace);
	}

	public QuotesException(String message) {
		super(message);
	}

	public QuotesException(Exception trace) {
		super(trace);
	}

}
